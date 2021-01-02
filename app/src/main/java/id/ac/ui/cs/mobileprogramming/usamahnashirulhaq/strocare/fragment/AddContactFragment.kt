package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.AddContactFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Contact
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.READ_CONTACT_PERMISSION
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.READ_CONTACT_PERMISSION_CODE
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.add_contact_fragment.*


class AddContactFragment : Fragment() {

    private lateinit var contactViewModel: ContactViewModel
    private var flagRequestPermissionCalled: Boolean = false
    private var permissionRequestCounter: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<AddContactFragmentBinding>(
            inflater,
            R.layout.add_contact_fragment,
            container,
            false
        )
        val namaKontak: EditText = binding.namaKontakField
        val nomorKontak: EditText = binding.nomorKontakField
        val buttonPilihKontak: Button = binding.buttonPilihKontak

        namaKontak.setOnClickListener {
            if (!checkPermission(READ_CONTACT_PERMISSION, requireContext())) {
                if (!flagRequestPermissionCalled) {
                    requestContactPermission(requireContext(), requireActivity())
                } else {
                    AlertDialog.Builder(context)
                        .setTitle(R.string.dialog_title_izin_ditolak)
                        .setMessage(R.string.dialog_message_izin_kontak_ditolak)
                        .setPositiveButton(
                            R.string.dialog_dismiss_button
                        ) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create().show()
                }
            } else {
                var intent = Intent(Intent.ACTION_PICK)
                intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
                startActivityForResult(intent, READ_CONTACT_PERMISSION_CODE)
            }
        }

        buttonPilihKontak.setOnClickListener {
            if (insertDataToDatabase(namaKontak.text.toString(), nomorKontak.text.toString())) {
                clearEditText(namaKontak, nomorKontak)
            }
        }
        return binding.root
    }

    private fun checkPermission(permission: String, context: Context): Boolean {
        var check: Int = ContextCompat.checkSelfPermission(context, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestContactPermission(context: Context, activity: Activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, READ_CONTACT_PERMISSION)
        ) {
            AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title_izin_dibutuhkan)
                .setMessage(R.string.dialog_message_izin_kontak_dibutuhkan)
                .setPositiveButton(
                    R.string.permission_dialog_positive_button
                ) { _, _ ->
                    requestPermissions(
                        arrayOf(READ_CONTACT_PERMISSION), READ_CONTACT_PERMISSION_CODE
                    )
                }
                .setNegativeButton(
                    R.string.permission_dialog_negative_button
                ) { dialog, _ -> dialog.dismiss() }
                .create().show()
        } else {
            requestPermissions(
                arrayOf(READ_CONTACT_PERMISSION), READ_CONTACT_PERMISSION_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == READ_CONTACT_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), R.string.request_result_diberikan, Toast.LENGTH_SHORT).show();
            } else {
                ++permissionRequestCounter
                if(permissionRequestCounter == 2) {
                    flagRequestPermissionCalled = true
                }
                Toast.makeText(requireContext(), R.string.request_result_ditolak, Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        contactPickHelper(
            requireActivity(),
            requestCode,
            resultCode,
            data,
            nomor_kontak_field,
            nama_kontak_field
        )
    }

    private fun contactPickHelper(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        nomor_kontak_field: EditText,
        nama_kontak_field: EditText
    ) {
        contactViewModel.contactPicker(
            activity,
            requestCode,
            resultCode,
            data,
            nomor_kontak_field,
            nama_kontak_field
        )
    }

    private fun insertDataToDatabase(
        namaKontak: String,
        nomorKontak: String,
    ): Boolean {
        if (inputCheck(namaKontak, nomorKontak)) {
            //create contact
            val newContact = Contact(0, namaKontak, nomorKontak)
            //add newContact to database
            contactViewModel.addContact(newContact)
            Toast.makeText(requireContext(), R.string.kontak_berhasil, Toast.LENGTH_SHORT)
                .show()
            return true
        } else {
            Toast.makeText(requireContext(), R.string.kontak_field_kurang, Toast.LENGTH_SHORT)
                .show()
            return false
        }
    }

    private fun inputCheck(namaKontak: String, nomorKontak: String): Boolean {
        return !(TextUtils.isEmpty(namaKontak) || TextUtils.isEmpty(nomorKontak))
    }

    fun clearEditText(namaKontak: EditText, nomorKontak: EditText) {
        namaKontak.text.clear()
        nomorKontak.text.clear()
    }
}