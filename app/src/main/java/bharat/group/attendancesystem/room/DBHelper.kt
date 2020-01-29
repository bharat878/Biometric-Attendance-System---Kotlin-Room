package bharat.group.attendancesystem.room

import android.content.Context
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee

class DBHelper(var employeeDatabse:EmployeeDatabase):DBHelperI {
    override fun insertEmployeeDetails(
        employeeData: Employee,
        context: Context?
    ) {

       // employeeDatabse.getEmployeeDao().insert(employeeData)

        EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()?.insert(employeeData)
    }
/*
    override fun insertEmployeeDetails(
        employeeCode: String,
        employeeName: String,
        employeEmail: String,
        employeePassword: String,
        employeeDob: String,
        employeePhoneNo: String
    ) {


    }*/
}