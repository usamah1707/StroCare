package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.ReportDao
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Report

class ReportRepository(private val reportDao: ReportDao) {

    val readAllData: LiveData<List<Report>> = reportDao.readAllData()

    suspend fun addReport(report: Report){
        reportDao.addReport(report)
    }

    suspend fun updateReport(report: Report){
        reportDao.updateReport(report)
    }

    suspend fun deleteReport(report: Report){
        reportDao.deleteReport(report)
    }
}