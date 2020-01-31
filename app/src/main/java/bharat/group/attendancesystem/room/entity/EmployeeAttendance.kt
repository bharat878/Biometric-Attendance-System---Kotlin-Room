package bharat.group.attendancesystem.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmployeeAttendance_table")
data class EmployeeAttendance (

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

    @ColumnInfo(name = "employee_code")
    var employee_code:String = "" ,

    @ColumnInfo(name = "employee_name")
    var employee_name:String = "",

    @ColumnInfo(name = "date")
    var date:String = "",

    @ColumnInfo(name = "present")
    var present:String = ""

    )
