package bharat.group.attendancesystem.ui

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.usage.StorageStats
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import bharat.group.attendancesystem.R
import bharat.group.attendancesystem.room.database.EmployeeDatabase
import bharat.group.attendancesystem.room.entity.Employee
import bharat.group.attendancesystem.ui.auth.LoginFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val STORAGE_PERMISSION_REQUEST_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermission()
    }

    private fun nextFragment() {

        val loginFragment:LoginFragment = LoginFragment()
        val fragmentManager:FragmentManager = supportFragmentManager
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, loginFragment).commit()
    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            nextFragment()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                nextFragment()
            } else if (isPermanentlyDenied()) {
                showGoToAppSettingsDialog()
            } else
                requestPermission()
        }
    }


    private fun isPermanentlyDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE).not()
        } else {
            return false
        }
    }

    private fun showGoToAppSettingsDialog() {
        AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setTitle(getString(R.string.grant_permissions))
            .setMessage(getString(R.string.we_need_permission))
            .setPositiveButton(getString(R.string.grant)) { _, _ ->
                goToAppSettings()
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                run {
                    finish()
                }
            }.show()
    }


    private fun goToAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        checkForPermission()
    }


}
