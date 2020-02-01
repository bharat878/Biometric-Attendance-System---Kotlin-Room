package bharat.group.attendancesystem.ui.crud


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker

import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.extension.gotoFrag
import bharat.group.attendancesystem.extension.showToast
import bharat.group.attendancesystem.room.DBHelper
import bharat.group.attendancesystem.room.DBHelperI
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.EmployeeAttendance
import kotlinx.android.synthetic.main.fragment_add_attendance.*
import kotlinx.android.synthetic.main.fragment_add_attendance.view.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddAttendanceFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    lateinit var mView: View
    lateinit var dbHelperI: DBHelperI
    val c = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_attendance, container, false)

        init()
        onClick()
        return mView
    }

    private fun init() {
        dbHelperI = DBHelper(EmployeeDatabase.getInstance(context!!)!!)

    }

    private fun onClick() {
        mView.employeeDateAttendance.setOnClickListener {
            datePicker()
        }

        mView.submitAttendance.setOnClickListener {
            getDetails()
        }

    }


    private fun datePicker() {
        val datePickerDialog = context?.let {
            DatePickerDialog(
                it,
                this,
                Calendar.getInstance()[Calendar.YEAR],
                Calendar.getInstance()[Calendar.MONTH],
                Calendar.getInstance()[Calendar.DAY_OF_MONTH]
            )
        }
        datePickerDialog!!.show()
    }

    override fun onDateSet(mView: DatePicker?, year: Int, month: Int, day: Int) {

        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        employeeDateAttendance.text = sdf.format(c.time)
    }

    private fun getDetails() {

        val employeeAttendance = EmployeeAttendance(
            employee_code = mView.employeeCodeAttendance.text.toString(),
            employee_name = mView.employeeNameAttendance.text.toString(),
            date = mView.employeeDateAttendance.text.toString(),
            present = mView.employeePresentAttendance.text.toString()
        )

        saveToDatabase(employeeAttendance)
    }

    private fun saveToDatabase(employeeAttendance: EmployeeAttendance) {
        dbHelperI.insertEmployeeAttendance(employeeAttendance, context)
        context!!.showToast("saved attendance")
        goBack()
    }

    private fun goBack() {
        context!!.gotoFrag(activity!!)
    }
}
