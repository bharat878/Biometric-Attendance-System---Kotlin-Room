package bharat.group.attendancesystem.ui.crud


import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.extension.showToast
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import bharat.group.attendancesystem.room.entity.EmployeeAttendance
import kotlinx.android.synthetic.main.fragment_download.view.*
import java.io.File
import java.io.FileWriter


/**
 * A simple [Fragment] subclass.
 */
class DownloadFragment : Fragment() {

    lateinit var mView: View
    lateinit var dbHelperI: DBHelperI

    var writer: FileWriter? = null
    lateinit var root: File
    lateinit var csvFIle: File
    lateinit var txt:File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_download, container, false)
        init()
        onClick()
        return mView
    }


    private fun init() {
        root = Environment.getExternalStorageDirectory()
        csvFIle = File(root, "attendance.csv")
        txt = File(root, "attendance.txt")

        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)

        //readEmployeeAttendance(csvFIle)
    }

    private fun onClick() {

        mView.csvFormat.setOnClickListener {
            readEmployeeAttendance(csvFIle)
            context!!.showToast("downloaded")
        }

        mView.txtFormat.setOnClickListener {
            readEmployeeAttendance(txt)
            context!!.showToast("downloaded")
        }
    }

    private fun readEmployeeAttendance(file: File) {
        val attendance:List<EmployeeAttendance> = dbHelperI.selectEmployeeAttendance(context)
        Log.d("attendance", "attendance : $attendance")

        val employee:List<Employee> = dbHelperI.selectEmployees(context)

        Log.d("employees", "attendance : $employee")

        download(attendance, file, employee)
    }

    private fun download(
        attendance: List<EmployeeAttendance>,
        file: File,
        employee: List<Employee>
    ) {
        val writer:FileWriter = FileWriter(file)
        writer.append("employee_code", ",","employee_name", ",", "date", ",", "present")
        writer.write("\n")

        attendance.forEach {
            writer.append(it.employee_code,",",it.employee_name,",",it.date,",",it.present,"\n")
        }

        writer.write("\n")
        writer.write("\n")

        writer.append("employee_code", ",","employee_name", ",", "employee_dob", ",", "employee_email", ",", "employee_phone")
        writer.write("\n")

        employee.forEach{
            writer.append(it.employee_code, ",", it.employee_name,",", it.employee_dob, ",", it.employee_email, ",", it.employee_phoneNo, "\n")
        }

        writer.flush()
        writer.close()
    }


}
