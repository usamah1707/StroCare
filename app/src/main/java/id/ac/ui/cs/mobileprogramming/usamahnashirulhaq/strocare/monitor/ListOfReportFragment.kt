package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.monitor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R

class ListOfReportFragment : Fragment(R.layout.list_of_report_fragment) {

    companion object {
        fun newInstance() = ListOfReportFragment()
    }

    private lateinit var viewModel: ListOfReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_report_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListOfReportViewModel::class.java)
        // TODO: Use the ViewModel
    }

}