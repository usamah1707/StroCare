package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var navBottomView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        navBottomView = binding.bottomNavView
        navBottomView.selectedItemId = R.id.miHome
        navBottomView.background = null
        navBottomView.menu.getItem(2).isEnabled = false
        navBottomView.setOnNavigationItemSelectedListener{onNavigationItemSelected(it)}

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
            R.id.miProfile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                overridePendingTransition(0,0)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        navBottomView.selectedItemId = R.id.miHome
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}