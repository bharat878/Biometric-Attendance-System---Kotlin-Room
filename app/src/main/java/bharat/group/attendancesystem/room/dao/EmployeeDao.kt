package bharat.group.attendancesystem.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import bharat.group.attendancesystem.room.entity.Employee
import bharat.group.attendancesystem.room.entity.EmployeeAttendance

@Dao
interface EmployeeDao {

    @Insert
    fun insert(employee: Employee)

    @Insert
    fun insertAttendance(employeeAttendance: EmployeeAttendance)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)

    @Query("SELECT fingerprint_scan FROM attendance_table where employee_code = :empCode")
    fun selectFingerPrintValue(empCode:String):Boolean

    @Query("SELECT employee_password FROM attendance_table Where employee_code = :employee")
    fun selectCredentials(employee: String):String

    @Query("SELECT * FROM attendance_table WHERE employee_code = :empCode")
    fun selectRow(empCode: String):Employee

    @Query("SELECT * FROM EmployeeAttendance_table")
    fun getAllEmployees(): List<EmployeeAttendance>

    @Query("SELECT * FROM attendance_table")
    fun getEmployeeDetails():List<Employee>

}