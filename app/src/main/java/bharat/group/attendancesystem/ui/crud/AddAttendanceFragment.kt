package bharat.group.attendancesystem.ui.crud


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.room.entity.EmployeeAttendance
import kotlinx.android.synthetic.main.fragment_add_attendance.view.*

/**
 * A simple [Fragment] subclass.
 */
class AddAttendanceFragment : Fragment() {

    lateinit var mView: View
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

    }

    private fun onClick() {
        mView.submitAttendance.setOnClickListener {
            getDetails()
        }

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


    }

}
