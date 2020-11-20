package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Schedule

@Database(entities = [Schedule::class], version = 1, exportSchema = false)
abstract class ScheduleDatabase: RoomDatabase(){

    abstract  fun scheduleDao(): ScheduleDao

    companion object{
        @Volatile
        private var INSTANCE: ScheduleDatabase? = null

        fun getDatabase(context: Context) : ScheduleDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleDatabase::class.java,
                    "schedule_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}