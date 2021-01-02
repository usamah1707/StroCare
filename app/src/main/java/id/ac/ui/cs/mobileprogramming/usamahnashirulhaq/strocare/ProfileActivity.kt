package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter.ContactAdapter
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityProfileBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.SMS_PERMISSION
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants.SMS_REQUEST_CODE
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.AuthViewModel
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ContactViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var navBottomView: BottomNavigationView
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var logoutButton: ImageView
    private var flagRequestPermissionCalled: Boolean = false
    private var permissionRequestCounter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        auth = Firebase.auth
        fStore = FirebaseFirestore.getInstance()
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        navBottomView = binding.bottomNavView
        navBottomView.selectedItemId = R.id.miProfile
        navBottomView.background = null
        navBottomView.menu.getItem(2).isEnabled = false
        navBottomView.setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }

        val adapter = ContactAdapter()
        val recycler = binding.contactRecyclerView

        //contact viewmodel
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        var contactData = contactViewModel.readAllData
        contactData.observe(this, Observer { contact ->
            adapter.setData(contact)
        })

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        recycler.setHasFixedSize(true)

        val navButton: FloatingActionButton = binding.buttonNavigationProfile

        navButton.setOnClickListener {
            if (!checkPermission(SMS_PERMISSION, this@ProfileActivity)) {
                if (!flagRequestPermissionCalled) {
                    requestSMSPermission(this@ProfileActivity, this@ProfileActivity)
                } else {
                    AlertDialog.Builder(this@ProfileActivity)
                        .setTitle(R.string.dialog_title_izin_ditolak)
                        .setMessage(R.string.dialog_message_izin_sms_ditolak)
                        .setPositiveButton(
                            R.string.dialog_dismiss_button
                        ) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create().show()
                }
            } else if (contactData.value!!.isNotEmpty()) {
                for (item in contactData.value!!) {
                    sendSms(this@ProfileActivity, item.nomorTelepon)
                }
            } else {
                Toast.makeText(this@ProfileActivity, R.string.terjadi_kesalahan, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        logoutButton = binding.buttonLogout
        logoutButton.setOnClickListener {
            logout()
        }

        retriveInfo(fStore, auth, binding.tvNamaAkun, binding.tvEmailAkun)

    }

    private fun checkPermission(permission: String, context: Context): Boolean {
        var check: Int = ContextCompat.checkSelfPermission(context, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestSMSPermission(context: Context, activity: Activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                SMS_PERMISSION
            )
        ) {
            AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title_izin_dibutuhkan)
                .setMessage(R.string.dialog_message_izin_sms_dibutuhkan)
                .setPositiveButton(
                    R.string.dialog_positive_button
                ) { _, _ ->
                    requestPermissions(
                        arrayOf(SMS_PERMISSION),
                        SMS_REQUEST_CODE
                    )
                }
                .setNegativeButton(
                    R.string.dialog_negative_button
                ) { dialog, _ -> dialog.dismiss() }
                .create().show()
        } else {
            requestPermissions(
                arrayOf(SMS_PERMISSION), SMS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@ProfileActivity,
                    R.string.request_result_diberikan,
                    Toast.LENGTH_SHORT
                ).show();
            } else {
                ++permissionRequestCounter
                if (permissionRequestCounter == 2) {
                    flagRequestPermissionCalled = true
                }
                Toast.makeText(
                    this@ProfileActivity,
                    R.string.request_result_ditolak,
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    }


    fun sendSms(context: Context, nomorTelepon: String) {
        contactViewModel.sendSms(context, nomorTelepon)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        Toast.makeText(applicationContext, R.string.berhasil_logout, Toast.LENGTH_SHORT).show()
        finish()
    }

    fun retriveInfo(
        fStore: FirebaseFirestore,
        auth: FirebaseAuth,
        tvNamaAkun: TextView,
        tvEmailAkun: TextView
    ) {
        authViewModel.retriveInfo(fStore, auth, tvNamaAkun, tvEmailAkun)
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miReport -> {
                startActivity(Intent(this, ReportActivity::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.miSchedule -> {
                startActivity(Intent(this, SchedulerActivity::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.miHome -> {
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }
        return true
    }


    override fun onResume() {
        super.onResume()
        navBottomView.selectedItemId = R.id.miProfile
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}