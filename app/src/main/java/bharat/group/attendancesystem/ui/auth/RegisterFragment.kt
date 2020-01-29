package bharat.group.attendancesystem.ui.auth

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker

import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    lateinit var mView: View
    val c = Calendar.getInstance()
    lateinit var dbHelperI: DBHelperI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_register, container, false)

        init()
        onClick()

        return mView
    }

    private fun init() {
        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)
    }

    private fun onClick() {
        mView.employeeDob.setOnClickListener {
            datePicker()
        }

        mView.submit.setOnClickListener {
            getDetails()
        }
    }

    private fun getDetails() {
        val employeeData = Employee(
            employee_code = mView.employeeCode.text.toString(),
            employee_name = mView.employeeName.text.toString(),
            employee_dob = mView.employeeDob.text.toString(),
            employee_email = mView.employeeEmail.text.toString(),
            employee_password = mView.employeePassword.text.toString(),
            employee_phoneNo = mView.employeePhoneNo.text.toString()
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
    }
}
