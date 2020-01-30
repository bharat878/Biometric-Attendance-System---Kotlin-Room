package bharat.group.attendancesystem.ui.auth


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.extension.showToast
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import bharat.group.attendancesystem.ui.AttendanceActivity
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    lateinit var mView:View
    lateinit var dbHelperI: DBHelperI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login, container, false)
        init()
        onClick()
        return mView
    }


    private fun init() {
        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)
    }


    private fun onClick() {

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


}
