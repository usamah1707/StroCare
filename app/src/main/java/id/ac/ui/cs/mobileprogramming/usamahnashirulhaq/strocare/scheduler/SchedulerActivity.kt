package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.scheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivitySchedulerBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.home.HomeActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.monitor.MonitorActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.profile.ProfileActivity

class SchedulerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchedulerBinding
    private lateinit var navBottomView : BottomNavigationView
    private var isListFragment : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scheduler)

        navBottomView = binding.bottomNavView
        navBottomView.selectedItemId = R.id.miSchedule
        navBottomView.background = null
        navBottomView.menu.getItem(2).isEnabled = false
        navBottomView.setOnNavigationItemSelectedListener{onNavigationItemSelected(it)}

        val listScheduleFragment = ListScheduleFragment()
        val addScheduleFragment = AddSchedulerFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(binding.flSchedule.id, listScheduleFragment)
            commit()
        }

        binding.buttonAddSchedule.setOnClickListener {
            if(isListFragment) {
                supportFragmentManager.beginTransaction().apply {
                    replace(binding.flSchedule.id, addScheduleFragment)
                    commit()
                    binding.buttonAddSchedule.text = resources.getString(R.string.tambahkan_jadwal)
                    isListFragment = false
                }
            }
            else{
                supportFragmentManager.beginTransaction().apply {
                    replace(binding.flSchedule.id, listScheduleFragment)
                    commit()
                    binding.buttonAddSchedule.text = resources.getString(R.string.tambah_jadwal_baru)
                    isListFragment = true
                }
            }
        }

    }

    fun onNavigationItemSelected(item: MenuItem): Boolean{
        when (item.itemId) {
            R.id.miMonitor -> {
                startActivity(Intent(this, MonitorActivity::class.java))
                overridePendingTransition(0,0)
            }
            R.id.miHome -> {
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(0,0)
            }
            R.id.miProfile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                overridePendingTransition(0,0)
            }
        }
        return true
    }
    override fun onResume() {
        super.onResume()
        navBottomView.selectedItemId = R.id.miSchedule
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}