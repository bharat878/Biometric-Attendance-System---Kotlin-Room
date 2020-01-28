package bharat.group.attendancesystem.room.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import bharat.group.attendancesystem.room.dao.EmployeeDao

abstract class EmployeeDatabase: RoomDatabase() {
    abstract fun EmployeeDao():EmployeeDao

    companion object{
        private const val TAG = "EMPLOYEE_DATABASE"
        private const val DATABASE_NAME = "employee_database.db"

        @Volatile
        private var INSTANCE: EmployeeDatabase? = null

        fun getInstance(context: Context):EmployeeDatabase?{
            if (INSTANCE==null){
                synchronized(this){
                    Log.d(TAG, " >>> Creating new database instance")
                    INSTANCE = Room
                        .databaseBuilder(context.applicationContext,
                            EmployeeDatabase::class.java,
                            DATABASE_NAME)
                        .build()
                }
            }
            Log.d(TAG, " >>> Getting the database instance")
            return INSTANCE
        }

        fun destroyInstance() {
            Log.d(TAG, " >>> Destroying app database instance")
            INSTANCE = null
        }
    }

}