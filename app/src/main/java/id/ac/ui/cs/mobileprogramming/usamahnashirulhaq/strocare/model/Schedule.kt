package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val namaObat: String,
    val dosisObat: String,
    val namaDokter: String,
    val waktuKonsumsi: String
): Parcelable