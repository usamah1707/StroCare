package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.ScheduleDao
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Schedule

class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    val readAllData: LiveData<List<Schedule>> = scheduleDao.readAllData()

    suspend fun addSchedule(schedule: Schedule){
        scheduleDao.addSchedule(schedule)
    }

    suspend fun updateSchedule(schedule: Schedule){
        scheduleDao.updateSchedule(schedule)
    }

    suspend fun deleteSchedule(schedule: Schedule){
        scheduleDao.deleteSchedule(schedule)
    }
}