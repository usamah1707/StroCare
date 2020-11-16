package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.scheduler

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R

class AddSchedulerFragment : Fragment(R.layout.add_schedule_fragment) {

    companion object {
        fun newInstance() = AddSchedulerFragment()
    }

    private lateinit var viewModel: AddSchedulerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_schedule_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddSchedulerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}