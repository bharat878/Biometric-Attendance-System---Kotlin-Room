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


    @Query("SELECT * FROM attendance_table ORDER BY employee_code DESC")
    fun getAllEmployees(): LiveData<List<Employee>>
}