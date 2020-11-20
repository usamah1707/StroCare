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
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ListReportFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter.ReportAdapter
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter.ScheduleAdapter
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ReportViewModel
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ScheduleViewModel

class ListReportFragment : Fragment() {

    private lateinit var viewModel: ReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ListReportFragmentBinding>(
            inflater,
            R.layout.list_report_fragment,
            container,
            false
        )


        val adapter = ReportAdapter()
        val recycler = binding.ReportRecyclerView

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)

        //schedule viewmodel
        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        viewModel.readAllData.observe(viewLifecycleOwner, Observer {report ->
            adapter.setData(report)
        })

        return binding.root
    }

}