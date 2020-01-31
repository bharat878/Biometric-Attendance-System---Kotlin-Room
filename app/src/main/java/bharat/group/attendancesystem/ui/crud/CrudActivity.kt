package bharat.group.attendancesystem.ui.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.ui.auth.LoginFragment

class CrudActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        val crudFragment = CrudFragment()
        supportFragmentManager.beginTransaction().replace(R.id.crud_container, crudFragment).commit()
    }
}
