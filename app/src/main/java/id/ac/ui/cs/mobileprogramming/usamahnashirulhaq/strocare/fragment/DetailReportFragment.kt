package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.DetailReportFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ReportViewModel

class DetailReportFragment : Fragment() {

    private lateinit var viewModel: ReportViewModel
    private val args by navArgs<DetailReportFragmentArgs>()
    private val listOfAnswer = listOf<String>("","Baik", "Agak buruk", "Buruk")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DetailReportFragmentBinding>(
            inflater,
            R.layout.detail_report_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)

        val hasilLaporan = binding.tvHasilLaporan
        val tanggalLaporan = binding.tvTanggalLaporan
        val pernyataan1 = binding.tvHasilQ1
        val pernyataan2 = binding.tvHasilQ2
        val pernyataan3 = binding.tvHasilQ3

        hasilLaporan.setText(args.currentReport.kesimpulanLaporan)
        tanggalLaporan.setText(args.currentReport.tanggalLaporan)
        pernyataan1.setText(listOfAnswer[args.currentReport.paramater_1])
        pernyataan2.setText(listOfAnswer[args.currentReport.paramater_2])
        pernyataan3.setText(listOfAnswer[args.currentReport.paramater_3])

        return binding.root
    }

}