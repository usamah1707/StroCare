package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Schedule

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSchedule(schedule: Schedule)

    @Update
    suspend fun updateSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Schedule>>
}