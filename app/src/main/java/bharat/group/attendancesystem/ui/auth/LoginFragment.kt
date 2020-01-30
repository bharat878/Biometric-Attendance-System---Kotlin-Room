package bharat.group.attendancesystem.ui.auth


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt

import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.extension.showToast
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import bharat.group.attendancesystem.ui.AttendanceActivity
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    lateinit var mView:View
    lateinit var dbHelperI: DBHelperI

    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var promptInfo : BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login, container, false)
        init()
        initBiometricPrompt()
        onClick()
        return mView
    }


    private fun init() {
        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)
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
                    activity!!.runOnUiThread {
                        context!!.showToast("Authentication Successful")
                        context!!.startActivity(Intent(context, AttendanceActivity::class.java))
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    activity!!.runOnUiThread {context!!.showToast("Authentication Failed")  }

                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(LoginFragment.TITLE)
            .setSubtitle(LoginFragment.SUBTITLE)
            .setDescription(LoginFragment.DISCRIPTION)
            .setNegativeButtonText(LoginFragment.CANCEL)
            .build()
    }

    @SuppressLint("ResourceType")
    private fun onClick() {

        mView.btnScan.setOnClickListener {

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val defaultValue:String = resources.getString(R.string.saved_high_score_default_key)
            val emp_code: String? = sharedPref!!.getString(getString(R.string.employee_code), defaultValue)

            readFingerprintvalue(emp_code)

        }

        mView.btnLogin.setOnClickListener {
            getDetails()
        }

        mView.btnSignUp.setOnClickListener {
            val registerFragment:RegisterFragment = RegisterFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, registerFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun getDetails() {
        val employeeData = Employee(
            employee_code = mView.loginEmployeeCode.text.toString(),
            employee_password = mView.loginPassword.text.toString()
        )
        checkValidation(employeeData)
    }

    private fun checkValidation(employeeData: Employee) {
        when {
            employeeData.employee_code.isEmpty() -> {
                mView.loginEmployeeCode.error = "Employee code"
                mView.loginEmployeeCode.requestFocus()
                return
            }

            employeeData.employee_password.isEmpty() ->{
                mView.loginPassword.error = "Password"
                mView.loginPassword.requestFocus()
            }
        }

        readToDatabase(employeeData)
    }

    private fun readToDatabase(employeeData: Employee) {
        val password:String = dbHelperI.selectEmployeePassword(context,employeeData.employee_code)

        if (employeeData.employee_password == password){
            context!!.startActivity(Intent(context, AttendanceActivity::class.java))
        }else{
            context!!.showToast("wrong credentials")
        }
    }


    private fun readFingerprintvalue(empCode: String?) {
        val fingerprintValue:Boolean = dbHelperI.selectEmployeeFingerprint(context,empCode!!)
       // context!!.showToast(fingerprintValue)

        if (fingerprintValue){
            showBiometricPrompts()

        }else{
            context!!.showToast("you're not registered fingerprint")
        }
    }

    private fun showBiometricPrompts() {
        biometricPrompt.authenticate(promptInfo)
    }

    companion object{
        private const val TITLE : String = "Authenticate Finger"
        private const val SUBTITLE : String = ""
        private const val DISCRIPTION : String = ""
        private const val CANCEL : String = "Cancel"
    }
}
