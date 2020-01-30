package bharat.group.attendancesystem.ui.auth

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.biometric.BiometricPrompt

import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.extension.showToast
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class RegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    lateinit var mView: View
    val c = Calendar.getInstance()
    lateinit var dbHelperI: DBHelperI
    var fingerprint:Boolean = false

    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var promptInfo : BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_register, container, false)

        init()
        initBiometricPrompt()
        onClick()

        return mView
    }

    private fun initBiometricPrompt() {
        val executor = Executors.newSingleThreadExecutor()

        biometricPrompt = BiometricPrompt(requireActivity(),
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON){
                        activity!!.runOnUiThread {context!!.showToast("$errString button clicked by user")  }
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    activity!!.runOnUiThread {context!!.showToast("Authentication Successful")  }
                    fingerprint = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    activity!!.runOnUiThread {context!!.showToast("Authentication Failed")  }

                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(TITLE)
            .setSubtitle(SUBTITLE)
            .setDescription(DISCRIPTION)
            .setNegativeButtonText(CANCEL)
            .build()
    }

    private fun init() {
        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)
    }

    private fun onClick() {
        mView.employeeDob.setOnClickListener {
            datePicker()
        }

        mView.scanFingerPrint.setOnClickListener {
            if (checkFingerPrintSetting()){
                showBiometricPrompts()
            }else{
                context!!.showToast("This feature is not available on your device")
            }
        }

        mView.submit.setOnClickListener {
            getDetails()
        }
    }

    private fun checkFingerPrintSetting(): Boolean {
        return BiomatricUtils.isHardwareSupported(context!!)
                && BiomatricUtils.isSdkVersionSupported()
                && BiomatricUtils.isFingerprintAvailable(context!!)
    }

    private fun showBiometricPrompts() {
        biometricPrompt.authenticate(promptInfo)
    }

    private fun getDetails() {
        val employeeData = Employee(
            employee_code = mView.employeeCode.text.toString(),
            employee_name = mView.employeeName.text.toString(),
            employee_dob = mView.employeeDob.text.toString(),
            employee_email = mView.employeeEmail.text.toString(),
            employee_password = mView.employeePassword.text.toString(),
            employee_phoneNo = mView.employeePhoneNo.text.toString(),
            employee_fingerprintScan = fingerprint
        )
        checkValidation(employeeData)
    }


    private fun datePicker() {
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it,
                this,
                Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                Calendar.getInstance()[Calendar.DAY_OF_MONTH]
            )
        }
        datePickerDialog!!.show()
    }

    override fun onDateSet(mView: DatePicker?, year: Int, month: Int, day: Int) {

        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        employeeDob.text = sdf.format(c.time)
    }


    private fun checkValidation(employeeData: Employee) {
        when{
            employeeData.employee_code.isEmpty() ->{
                mView.employeeCode.error = "Employee code"
                mView.employeeCode.requestFocus()
                return
            }

            employeeData.employee_name.isEmpty() ->{
                mView.employeeName.error = "Employee name"
                mView.employeeName.requestFocus()
                return
            }

            employeeData.employee_dob.isEmpty() ->{
                mView.employeeDob.error = "Date of Birth"
                mView.employeeDob.requestFocus()
                return
            }

            employeeData.employee_email.isEmpty() ->{
                mView.employeeEmail.error = "Email"
                mView.employeeEmail.requestFocus()
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(employeeData.employee_email).matches() -> {
                mView.employeeEmail.error = "Valid Email Required"
                mView.employeeEmail.requestFocus()
                return
            }

            employeeData.employee_password.isEmpty() ->{
                mView.employeePassword.error = "Password"
                mView.employeePassword.requestFocus()
            }

            employeeData.employee_phoneNo.isEmpty() ->{
                mView.employeePhoneNo.error = "Phone No"
                mView.employeePhoneNo.requestFocus()
            }
        }

            saveToDatabase(employeeData)
    }

    private fun saveToDatabase(employeeData: Employee) {
        dbHelperI.insertEmployeeDetails(employeeData, context)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.employee_code), employeeData.employee_code)
            commit()
        }

    }

    companion object{
        private const val TITLE : String = "Authenticate Finger"
        private const val SUBTITLE : String = ""
        private const val DISCRIPTION : String = ""
        private const val CANCEL : String = "Cancel"
    }
}
