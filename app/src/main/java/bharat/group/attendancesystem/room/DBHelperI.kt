package bharat.group.attendancesystem.room

import android.content.Context
import bharat.group.attendancesystem.room.entity.Employee

interface DBHelperI {

    fun insertEmployeeDetails(
        employeeData: Employee,
        context: Context?
    )

    fun selectEmployeePassword(
        context: Context?,
        employeeData: String
    ):String

    fun selectEmployeeFingerprint(
        context: Context?,
        empCode:String
    ):Boolean
}