package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.AddContactFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Contact
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.add_contact_fragment.*

class AddContactFragment : Fragment() {

    private val CONTACT_READ_REQUEST_CODE: Int = 111
    private lateinit var viewModel: ContactViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<AddContactFragmentBinding>(
            inflater,
            R.layout.add_contact_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        var namaKontak: EditText = binding.namaKontakField
        var nomorKontak: EditText = binding.nomorKontakField
        var buttonPilihKontak: Button = binding.buttonPilihKontak

        namaKontak.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(intent, CONTACT_READ_REQUEST_CODE)
        }

        buttonPilihKontak.setOnClickListener {
            if (insertDataToDatabase(namaKontak.text.toString(), nomorKontak.text.toString())) {
                clearEditText(it)
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        contactPickHelper(
            requireContext(),
            requireActivity(),
            requestCode,
            resultCode,
            data,
            nomor_kontak_field,
            nama_kontak_field
        )

    }

    private fun contactPickHelper(
        context: Context,
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        nomor_kontak_field: EditText,
        nama_kontak_field: EditText
    ) {
        viewModel.contactPicker(
            context,
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
            val contact = Contact(0, namaKontak, nomorKontak)
            //add report to database
            viewModel.addContact(contact)

            Toast.makeText(requireContext(), "Berhasil menambahkan kontak!", Toast.LENGTH_LONG)
                .show()
            return true
        } else {
            Toast.makeText(requireContext(), "Semua kolom harus diisi", Toast.LENGTH_LONG)
                .show()
            return false
        }
    }

    private fun inputCheck(namaKontak: String, nomorKontak: String): Boolean {
        return !(TextUtils.isEmpty(namaKontak) || TextUtils.isEmpty(nomorKontak))
    }

    fun clearEditText(view: View) {
        nama_kontak_field.text.clear()
        nomor_kontak_field.text.clear()
    }
}