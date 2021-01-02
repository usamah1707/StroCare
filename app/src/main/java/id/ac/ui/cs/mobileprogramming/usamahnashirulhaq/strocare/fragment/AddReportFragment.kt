package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.AddReportFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Report
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ReportViewModel
import java.text.DateFormat
import java.util.*

class AddReportFragment : Fragment() {

    private lateinit var reportViewModel: ReportViewModel
    private var position1 = 0
    private var position2 = 0
    private var position3 = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<AddReportFragmentBinding>(
            inflater,
            R.layout.add_report_fragment,
            container,
            false
        )

        reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)

        val question1: Spinner = binding.spinnerJawabanReport1
        val question2: Spinner = binding.spinnerJawabanReport2
        val question3: Spinner = binding.spinnerJawabanReport3
        val addReportButton = binding.buttonAddReport


        question1.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                position1 = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
        }

        question2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                position2 = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
        }

        question3.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                position3 = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}
        }

        addReportButton.setOnClickListener {
            if (insertDataToDatabase(position1, position2, position3)) {
                question1.setSelection(0)
                question2.setSelection(0)
                question3.setSelection(0)
            }
        }
        return binding.root
    }

    private fun insertDataToDatabase(
        question1: Int,
        question2: Int,
        question3: Int,
    ): Boolean {
        if (inputCheck(question1, question2, question3)) {
            val listofConclusion = listOf<String>(
                getString(R.string.sehat),
                getString(R.string.kurang_sehat),
                getString(R.string.tidak_sehat))
            val failConclude : String = getString(R.string.gagal_menyimpulkan)
            //create report
            val kesimpulan = reportViewModel.makeConclusion(question1, question2, question3, listofConclusion, failConclude)
            val currentDate = getCurrentDate()
            val report = Report(0, currentDate, kesimpulan, question1, question2, question3)
            //add report to database
            reportViewModel.addReport(report)

            Toast.makeText(requireContext(), R.string.laporan_berhasil, Toast.LENGTH_SHORT)
                .show()
            return true
        } else {
            Toast.makeText(requireContext(), R.string.laporan_field_kurang, Toast.LENGTH_SHORT)
                .show()
            return false
        }
    }

    private fun inputCheck(question1: Int, question2: Int, question3: Int): Boolean {
        return !(question1 < 1 || question2 < 1 || question3 < 1)
    }

    private fun getCurrentDate(): String {
        var calendar = Calendar.getInstance()
        var currentDate = DateFormat.getDateInstance(Calendar.SHORT).format(calendar.time)

        return currentDate
    }

}