package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityReportBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment.AddReportFragment
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment.ListReportFragment

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private lateinit var navBottomView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report)

        navBottomView = binding.bottomNavView
        navBottomView.selectedItemId = R.id.miReport
        navBottomView.background = null
        navBottomView.menu.getItem(2).isEnabled = false
        navBottomView.setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }

        val navButton: FloatingActionButton = binding.buttonNavigationReport
        val navController = findNavController(R.id.report_fragment)

        navButton.setOnClickListener {
            navigate(navController, navButton)
        }

    }

    fun navigate(navController: NavController, navButton: FloatingActionButton){
        if(navController.currentDestination?.label == "List") {
            navController.navigate(R.id.action_listReportFragment_to_addReportFragment)
            navButton.setImageResource(R.drawable.ic_list)
        }
        else if(navController.currentDestination?.label == "Add"){
            navController.navigate(R.id.action_addReportFragment_to_listReportFragment)
            navButton.setImageResource(R.drawable.ic_add)
        }
        else if(navController.currentDestination?.label == "Detail"){
            navController.navigate(R.id.action_detailReportFragment_to_addReportFragment)
            navButton.setImageResource(R.drawable.ic_add)
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
        navBottomView.selectedItemId = R.id.miReport
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}
