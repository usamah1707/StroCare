package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Contact>>


}