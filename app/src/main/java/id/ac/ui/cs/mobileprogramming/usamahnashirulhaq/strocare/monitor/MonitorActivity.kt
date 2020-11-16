package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.monitor

import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityMonitorBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.home.HomeActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.profile.ProfileActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.scheduler.SchedulerActivity

class MonitorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMonitorBinding
    private lateinit var navBottomView : BottomNavigationView
    private var isListFragment : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_monitor)

        navBottomView = binding.bottomNavView
        navBottomView.selectedItemId = R.id.miMonitor
        navBottomView.background = null
        navBottomView.menu.getItem(2).isEnabled = false
        navBottomView.setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }

        val addReportFragment = AddReportFragment()
        val listOfReportFragment = ListOfReportFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(binding.flSchedule.id, listOfReportFragment)
            commit()
        }

        binding.buttonAddReport.setOnClickListener {
            if(isListFragment) {
                supportFragmentManager.beginTransaction().apply {
                    replace(binding.flSchedule.id, addReportFragment)
                    commit()
                    binding.buttonAddReport.text = resources.getString(R.string.tambah_laporan_mandiri)
                    isListFragment = false
                }
            }
            else{
                supportFragmentManager.beginTransaction().apply {
                    replace(binding.flSchedule.id, listOfReportFragment)
                    commit()
                    binding.buttonAddReport.text = resources.getString(R.string.tambah_laporan_baru)
                    isListFragment = true
                }
            }
        }

    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miHome -> {
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.miSchedule -> {
                startActivity(Intent(this, SchedulerActivity::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.miProfile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }
        return false
    }
    override fun onResume() {
        super.onResume()
        navBottomView.selectedItemId = R.id.miMonitor
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}
