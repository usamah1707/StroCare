package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel

import android.Manifest
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val CONTACT_READ_REQUEST_CODE: Int = 111
    private val READ_CONTACT_PERMISSION: String = Manifest.permission.READ_CONTACTS
    private val SMS_PERMISSION: String = Manifest.permission.SEND_SMS
    private val PESAN: String = R.string.pesan_sms.toString()


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
        context: Context,
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        nomor_kontak_field: EditText,
        nama_kontak_field: EditText
    ) {
        if (checkPermission(READ_CONTACT_PERMISSION, context)) {
            if (requestCode == CONTACT_READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
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
        } else {
            Toast.makeText(context, R.string.izin_read_kontak, Toast.LENGTH_LONG).show()
        }
    }

    fun sendSms(context: Context, nomorTelepon: String) {
        if (checkPermission(SMS_PERMISSION, context)) {
            var smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(nomorTelepon, null, PESAN, null, null)
            Toast.makeText(context, R.string.sms_berhasil, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, R.string.izin_sms, Toast.LENGTH_LONG).show()
        }
    }

    fun checkPermission(permissiom: String, context: Context): Boolean {
        var check: Int = ContextCompat.checkSelfPermission(context, permissiom)
        return (check == PackageManager.PERMISSION_GRANTED)
    }
}