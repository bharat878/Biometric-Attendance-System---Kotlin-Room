package bharat.group.attendancesystem.ui.auth


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager

import bharat.group.attendancesystem.R
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.concurrent.Executors

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    lateinit var mView:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login, container, false)
        init()
        onClick()
        return mView
    }


    private fun init() {
    }


    private fun onClick() {
        mView.btnSignUp.setOnClickListener {
            val registerFragment:RegisterFragment = RegisterFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, registerFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }



}
