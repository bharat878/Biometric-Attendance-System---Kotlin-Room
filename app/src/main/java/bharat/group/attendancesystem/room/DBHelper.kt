package bharat.group.attendancesystem.room

import android.content.Context
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import bharat.group.attendancesystem.room.entity.EmployeeAttendance

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

    override fun selectEmployeeFingerprint(context: Context?, empCode: String): Boolean {
        val fingerprint: Boolean = EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()!!.selectFingerPrintValue(empCode)
        return fingerprint
    }

    override fun selectEmployeeDetails(context: Context?, empCode: String): Employee {
        val employee:Employee = EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()!!.selectRow(empCode)
        return employee
    }

    override fun updateDetails(
        employeeData: Employee,
        context: Context?
    ) {
        EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()!!.update(employeeData)
    }

    override fun insertEmployeeAttendance(
        employeeAttendance: EmployeeAttendance,
        context: Context?
    ) {
        EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()?.insertAttendance(employeeAttendance)

    }

    override fun selectEmployeeAttendance(
        context: Context?
    ): List<EmployeeAttendance> {

        val attendance:List<EmployeeAttendance> = EmployeeDatabase.getInstance(context!!)?.getEmployeeDao()!!.getAllEmployees()
        return attendance
    }

}