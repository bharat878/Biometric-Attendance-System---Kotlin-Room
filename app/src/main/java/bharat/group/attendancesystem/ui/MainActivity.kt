package bharat.group.attendancesystem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import bharat.group.attendancesystem.ui.auth.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val employeeDetails = Employee(employee_code = "102", employee_name = "bharat", employee_email = "bharat@gmail.com", employee_dob = "26jan",
            employee_password = "123456", employee_phoneNo = "98765444")

        EmployeeDatabase.getInstance(this)?.getEmployeeDao()?.insert(employeeDetails)

        val loginFragment:LoginFragment = LoginFragment()
        val fragmentManager:FragmentManager = supportFragmentManager
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, loginFragment).commit()
    }
}
