package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ListScheduleFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter.ScheduleAdapter
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ScheduleViewModel

class ListScheduleFragment : Fragment() {


    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ListScheduleFragmentBinding>(
            inflater,
            R.layout.list_schedule_fragment,
            container,
            false
        )

        //recycler view
        val adapter = ScheduleAdapter()
        val recycler = binding.scheduleRecyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)

        //schedule viewmodel
        scheduleViewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        scheduleViewModel.readAllData.observe(viewLifecycleOwner, Observer { schedule ->
            adapter.setData(schedule)
        })
        return binding.root

    }

}