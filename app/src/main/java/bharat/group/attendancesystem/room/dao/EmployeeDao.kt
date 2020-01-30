package bharat.group.attendancesystem.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import bharat.group.attendancesystem.room.entity.Employee

@Dao
interface EmployeeDao {

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)

    @Query("SELECT employee_password FROM attendance_table Where employee_code = :employee")
    fun selectCredentials(employee: String):String

    @Query("SELECT * FROM attendance_table ORDER BY employee_code DESC")
    fun getAllEmployees(): List<Employee>
}