package bharat.group.attendancesystem.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import bharat.group.attendancesystem.room.entity.Employee

interface EmployeeDao {

    @Insert
    fun insert(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Delete
    fun delete(employee: Employee)


    @Query("SELECT * FROM attendance_table ORDER BY employee_code DESC")
    fun getAllEmployees(): LiveData<List<Employee>>
}