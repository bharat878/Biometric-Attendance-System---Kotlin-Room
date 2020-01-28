package bharat.group.attendancesystem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.ui.auth.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginFragment:LoginFragment = LoginFragment()
        val fragmentManager:FragmentManager = supportFragmentManager
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, loginFragment).commit()
    }
}
