package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nama: String,
    val nomorTelepon: String
): Parcelable