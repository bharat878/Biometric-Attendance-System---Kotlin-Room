package bharat.group.attendancesystem.ui.crud


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker

import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.extension.gotoFrag
import bharat.group.attendancesystem.extension.showToast
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class UpdateFragment : Fragment(), DatePickerDialog.OnDateSetListener  {

    lateinit var mView:View
    lateinit var dbHelperI: DBHelperI
    val c = Calendar.getInstance()
    var emp_id:Int = 0
    var fingerprint:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_update, container, false)
        init()
        onClick()
        return mView
    }

    private fun init() {
        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)

        val sharedPref = activity?.getSharedPreferences("AttendanceSystem",Context.MODE_PRIVATE)
        val defaultValue:String = resources.getString(R.string.saved_high_score_default_key)
        val emp_code: String? = sharedPref!!.getString(getString(R.string.employee_code), defaultValue)

        val employee:Employee = dbHelperI.selectEmployeeDetails(context, emp_code!!)

        emp_id = employee.id!!
        fingerprint = employee.employee_fingerprintScan
        prefilData(employee)
    }

    private fun prefilData(employee: Employee) {
        mView.updateEmployeeCode.setText(employee.employee_code)
        mView.updateEmployeeName.setText(employee.employee_name)
        mView.updateEmployeeDob.setText(employee.employee_dob)
        mView.updateEmployeeEmail.setText(employee.employee_email)
        mView.updateEmployeePassword.setText(employee.employee_password)
        mView.updateEmployeePhoneNo.setText(employee.employee_phoneNo)
    }

    private fun onClick() {
        mView.updateEmployeeDob.setOnClickListener {
            datePicker()
        }

        mView.updateDetails.setOnClickListener {
            getDetails()
        }
    }

    private fun datePicker() {
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it,
               this ,
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
        updateEmployeeDob.text = sdf.format(c.time)
    }
    private fun getDetails() {

        val employeeData = Employee(
            id = emp_id,
            employee_code = mView.updateEmployeeCode.text.toString(),
            employee_name = mView.updateEmployeeName.text.toString(),
            employee_dob = mView.updateEmployeeDob.text.toString(),
            employee_email = mView.updateEmployeeEmail.text.toString(),
            employee_password = mView.updateEmployeePassword.text.toString(),
            employee_phoneNo = mView.updateEmployeePhoneNo.text.toString(),
            employee_fingerprintScan = fingerprint
        )
        checkValidation(employeeData)

    }

    private fun checkValidation(employeeData: Employee) {
        when{
            employeeData.employee_code.isEmpty() ->{
                mView.updateEmployeeCode.error = "Employee code"
                mView.updateEmployeeCode.requestFocus()
                return
            }

            employeeData.employee_name.isEmpty() ->{
                mView.updateEmployeeName.error = "Employee name"
                mView.updateEmployeeName.requestFocus()
                return
            }

            employeeData.employee_dob.isEmpty() ->{
                mView.updateEmployeeDob.error = "Date of Birth"
                mView.updateEmployeeDob.requestFocus()
                return
            }

            employeeData.employee_email.isEmpty() ->{
                mView.updateEmployeeEmail.error = "Email"
                mView.updateEmployeeEmail.requestFocus()
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(employeeData.employee_email).matches() -> {
                mView.updateEmployeeEmail.error = "Valid Email Required"
                mView.updateEmployeeEmail.requestFocus()
                return
            }

            employeeData.employee_password.isEmpty() ->{
                mView.updateEmployeePassword.error = "Password"
                mView.updateEmployeePassword.requestFocus()
            }

            employeeData.employee_phoneNo.isEmpty() ->{
                mView.updateEmployeePhoneNo.error = "Phone No"
                mView.updateEmployeePhoneNo.requestFocus()
            }
        }

        updateToDatabase(employeeData)
    }

    private fun updateToDatabase(employeeData: Employee) {
        dbHelperI.updateDetails(employeeData, context)
        context!!.showToast("updated")

        context!!.gotoFrag(activity!!)


    }

}
