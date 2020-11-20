package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter.ContactAdapter
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityProfileBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ContactViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var navBottomView : BottomNavigationView
    private lateinit var viewModel : ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

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
                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun sendSms(context: Context, nomorTelepon: String){
        viewModel.sendSms(context, nomorTelepon)
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