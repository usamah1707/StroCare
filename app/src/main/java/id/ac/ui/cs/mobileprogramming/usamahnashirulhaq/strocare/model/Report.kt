package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "report_table")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tanggalLaporan: String,
    val kesimpulanLaporan:  String,
    val paramater_1: Int,
    val paramater_2: Int,
    val paramater_3: Int,
):Parcelable