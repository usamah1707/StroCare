package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityProfileBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.home.HomeActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.monitor.MonitorActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.scheduler.SchedulerActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var navBottomView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        navBottomView = binding.bottomNavView
        navBottomView.selectedItemId = R.id.miProfile
        navBottomView.background = null
        navBottomView.menu.getItem(2).isEnabled = false
        navBottomView.setOnNavigationItemSelectedListener{onNavigationItemSelected(it)}

    }

    fun onNavigationItemSelected(item: MenuItem): Boolean{
        when (item.itemId) {
            R.id.miMonitor -> {
                startActivity(Intent(this, MonitorActivity::class.java))
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