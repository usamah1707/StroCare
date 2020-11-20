package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.ScheduleDatabase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Schedule
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.repository.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Schedule>>
    private val repository: ScheduleRepository
    private val context = getApplication<Application>().applicationContext

    init {
        val scheduleDao = ScheduleDatabase.getDatabase(application).scheduleDao()
        repository = ScheduleRepository(scheduleDao)
        readAllData = repository.readAllData
    }

    fun addSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSchedule(schedule)
        }
    }

    fun updateSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSchedule(schedule)
        }
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSchedule(schedule)
        }
    }



}