package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivitySchedulerBinding

class SchedulerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchedulerBinding
    private lateinit var navBottomView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scheduler)

        navBottomView = binding.bottomNavView
        navBottomView.selectedItemId = R.id.miSchedule
        navBottomView.background = null
        navBottomView.menu.getItem(2).isEnabled = false
        navBottomView.setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }

        val navButton: FloatingActionButton = binding.buttonNavigationSchedule
        val navController = findNavController(R.id.schedule_fragment)

        navButton.setOnClickListener {
            navigate(navController, navButton)
        }

    }

    fun navigate(navController: NavController, navButton: FloatingActionButton) {
        if (navController.currentDestination?.label == "List") {
            navController.navigate(R.id.action_listScheduleFragment_to_addSchedulerFragment)
            navButton.setImageResource(R.drawable.ic_list)
        } else if (navController.currentDestination?.label == "Add") {
            navController.navigate(R.id.action_addSchedulerFragment_to_listScheduleFragment)
            navButton.setImageResource(R.drawable.ic_add)
        } else if (navController.currentDestination?.label == "Update") {
            navController.navigate(R.id.action_updateScheduleFragment_to_listScheduleFragment)
            navButton.setImageResource(R.drawable.ic_add)
        }
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miReport -> {
                startActivity(Intent(this, ReportActivity::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.miHome -> {
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(0, 0)
            }
            R.id.miProfile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                overridePendingTransition(0, 0)
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
        overridePendingTransition(0, 0)
    }
}