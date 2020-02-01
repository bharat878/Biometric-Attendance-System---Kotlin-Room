package bharat.group.attendancesystem.extension

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.ui.crud.CrudFragment


fun Context.showToast(message : String){
    Toast.makeText(this,message, Toast.LENGTH_LONG).show()

}

fun Context.gotoFrag(activity: FragmentActivity) {

    val crudFragment = CrudFragment()
    activity.supportFragmentManager.beginTransaction().replace(R.id.crud_container, crudFragment).commit()
}