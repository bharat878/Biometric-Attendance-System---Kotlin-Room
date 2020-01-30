package bharat.group.attendancesystem.room

import android.content.Context
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee

class DBHelper(var employeeDatabse:EmployeeDatabase):DBHelperI {
    override fun insertEmployeeDetails(
        employeeData: Employee,
        context: Context?
    ) {
        EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()?.insert(employeeData)
    }

    override fun selectEmployeePassword(
        context: Context?,
        employeeData: String
    ):String {
         val credentials:String = EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()!!.selectCredentials(employeeData)
        return credentials
    }
}