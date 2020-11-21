package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.ReportDatabase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Report
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.repository.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Report>>
    private val repository: ReportRepository

    init {
        val ReportDao = ReportDatabase.getDatabase(application).ReportDao()
        repository = ReportRepository(ReportDao)
        readAllData = repository.readAllData
    }

    fun addReport(report: Report) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addReport(report)
        }
    }

    fun makeConclusion(q1: Int, q2: Int, q3: Int, listofConclusion: List<String>, failConclude: String): String {

        var sumHealthPoint = q1 + q2 + q3
        if (sumHealthPoint == 3) {
            return listofConclusion[0]
        }
        if (sumHealthPoint > 3 && sumHealthPoint <= 6) {
            return listofConclusion[1]
        }
        if (sumHealthPoint > 6 && sumHealthPoint <= 9) {
            return listofConclusion[2]
        }
        else {
            return failConclude
        }
    }

}