package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.ContactDatabase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Contact
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.repository.ContactRepository
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.READ_CONTACT_PERMISSION
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.READ_CONTACT_PERMISSION_CODE
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.SMS_PERMISSION
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.SMS_REQUEST_CODE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val PESAN: String = application.getString(R.string.pesan_sms)

    val readAllData: LiveData<List<Contact>>
    private val repository: ContactRepository

    init {
        val contactDao = ContactDatabase.getDatabase(application).ContactDao()
        repository = ContactRepository(contactDao)
        readAllData = repository.readAllData
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addContact(contact)
        }
    }

    fun contactPicker(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        nomor_kontak_field: EditText,
        nama_kontak_field: EditText
    ) {
        if (requestCode == READ_CONTACT_PERMISSION_CODE && resultCode == Activity.RESULT_OK) {
            var contactUri: Uri = data?.data ?: return
            var arrayOfContact: Array<String> = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            var query =
                activity.contentResolver!!.query(contactUri, arrayOfContact, null, null, null)
            if (query?.moveToFirst()!!) {
                nomor_kontak_field.setText(query.getString(0).toString())
                nama_kontak_field.setText(query.getString(1).toString())
            }
            query.close()
        }
    }

    fun sendSms(context: Context, nomorTelepon: String) {
        var smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(nomorTelepon, null, PESAN, null, null)
        Toast.makeText(context, R.string.sms_berhasil, Toast.LENGTH_SHORT).show()
    }
}