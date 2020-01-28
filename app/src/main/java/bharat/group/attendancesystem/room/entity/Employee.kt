package bharat.group.attendancesystem.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "attendance_table")
data class Employee (
    @ColumnInfo(name = "employee_code")
    var employee_code:String = "",

    @ColumnInfo(name = "employee_name")
    var employee_name:String = "",

    @ColumnInfo(name = "employee_email")
    var employee_email:String = "",

    @ColumnInfo(name = "employee_password")
    var employee_password:String = "",

    @ColumnInfo(name = "employee_dob")
    var employee_dob:String = "",

    @ColumnInfo(name = "employee_phoneNo")
    var employee_phoneNo:String = ""

)