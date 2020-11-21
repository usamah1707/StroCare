package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter.ContactAdapter
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityProfileBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.AuthViewModel
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ContactViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var navBottomView : BottomNavigationView
    private lateinit var viewModel : ContactViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var logoutButton: ImageView
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
        navBottomView.setOnNavigationItemSelectedListener{onNavigationItemSelected(it)}

        val adapter = ContactAdapter()
        val recycler = binding.contactRecyclerView

        //contact viewmodel
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        var contactData = viewModel.readAllData
        contactData.observe(this, Observer { contact ->
            adapter.setData(contact)
        })

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(applicationContext)
        recycler.setHasFixedSize(true)

        val navButton: FloatingActionButton = binding.buttonNavigationProfile

        navButton.setOnClickListener {
            if (contactData.value!!.isNotEmpty()) {
                for (item in contactData.value!!){
                    sendSms(applicationContext, item.nomorTelepon)
                }
            }
            else {
                Toast.makeText(applicationContext, R.string.terjadi_kesalahan, Toast.LENGTH_LONG).show()
            }
        }

        logoutButton = binding.buttonLogout
        logoutButton.setOnClickListener {
            logout()
        }

        retriveInfo(fStore, auth, binding.tvNamaAkun, binding.tvEmailAkun)

    }

    fun sendSms(context: Context, nomorTelepon: String){
        viewModel.sendSms(context, nomorTelepon)
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        Toast.makeText(applicationContext, R.string.berhasil_logout, Toast.LENGTH_SHORT).show()
        finish()
    }

    fun retriveInfo(fStore: FirebaseFirestore, auth: FirebaseAuth, tvNamaAkun: TextView, tvEmailAkun: TextView){
        authViewModel.retriveInfo(fStore, auth, tvNamaAkun, tvEmailAkun)
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean{
        when (item.itemId) {
            R.id.miReport -> {
                startActivity(Intent(this, ReportActivity::class.java))
                overridePendingTransition(0,0)
            }
            R.id.miSchedule -> {
                startActivity(Intent(this, SchedulerActivity::class.java))
                overridePendingTransition(0,0)
            }
            R.id.miHome -> {
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(0,0)
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
        overridePendingTransition(0,0)
    }
}