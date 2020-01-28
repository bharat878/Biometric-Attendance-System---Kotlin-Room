package bharat.group.attendancesystem.room

import android.app.Application
import com.facebook.stetho.Stetho

class MainApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}