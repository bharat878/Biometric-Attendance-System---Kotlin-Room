package bharat.group.attendancesystem.room

import android.content.Context
import bharat.group.attendancesystem.room.entity.Employee

interface DBHelperI {

    fun insertEmployeeDetails(
        employeeData: Employee,
        context: Context?
    )
/*
    fun insertEmployeeDetails(
        employeeCode:String,
        employeeName:String,
        employeEmail:String,
        employeePassword:String,
        employeeDob:String,
        employeePhoneNo:String
    )*/
}