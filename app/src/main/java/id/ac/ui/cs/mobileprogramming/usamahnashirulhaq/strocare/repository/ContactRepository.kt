package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.repository

import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.ContactDao
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Contact

class ContactRepository(private val contactDao: ContactDao) {

    val readAllData: LiveData<List<Contact>> = contactDao.readAllData()

    suspend fun addContact(contact: Contact){
        contactDao.addContact(contact)
    }

    suspend fun updateContact(contact: Contact){
        contactDao.updateContact(contact)
    }

    suspend fun deleteContact(contact: Contact){
        contactDao.deleteContact(contact)
    }
}