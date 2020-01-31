package bharat.group.attendancesystem.ui.crud


import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.EmployeeAttendance
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_download, container, false)
        init()
        return mView
    }

    private fun init() {
        root = Environment.getExternalStorageDirectory()
        csvFIle = File(root, "contacts.csv")

        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)

        readEmployeeAttendance()
    }

    private fun readEmployeeAttendance() {
        val attendance:List<EmployeeAttendance> = dbHelperI.selectEmployeeAttendance(context)
        Log.d("attendance", "attendance : $attendance")

        download(attendance)
    }

    private fun download(attendance: List<EmployeeAttendance>) {
        val writer:FileWriter = FileWriter(csvFIle)
        writer.append("employee_code", ",","employee_name", ",", "date", ",", "present")
        writer.write("\n")

        attendance.forEach {
            writer.append(it.employee_code,",",it.employee_name,",",it.date,",",it.present,",","\n")
        }

        writer.flush()
        writer.close()
    }


}
