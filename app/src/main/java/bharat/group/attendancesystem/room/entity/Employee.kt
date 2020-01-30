package bharat.group.attendancesystem.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "attendance_table")
data class Employee (

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

    @ColumnInfo(name = "employee_code")
    var employee_code:String = "" ,

    @ColumnInfo(name = "employee_name")
    var employee_name:String = "",

    @ColumnInfo(name = "employee_dob")
    var employee_dob:String = "",

    @ColumnInfo(name = "employee_email")
    var employee_email:String = "",

    @ColumnInfo(name = "employee_password")
    var employee_password:String = "",

    @ColumnInfo(name = "employee_phoneNo")
    var employee_phoneNo:String = "",

    @ColumnInfo(name = "fingerprint_scan")
    var employee_fingerprintScan:Boolean = false

)
