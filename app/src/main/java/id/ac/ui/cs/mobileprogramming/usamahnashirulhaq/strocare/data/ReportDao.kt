package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Report

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReport(report: Report)

    @Update
    suspend fun updateReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)

    @Query("SELECT * FROM report_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Report>>


}