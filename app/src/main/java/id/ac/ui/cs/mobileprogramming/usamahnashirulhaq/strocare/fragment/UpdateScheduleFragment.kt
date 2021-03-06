package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment

import android.app.AlertDialog
import android.app.AlertDialog.*
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.UpdateScheduleFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Schedule
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment.UpdateScheduleFragmentArgs
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ScheduleViewModel

class UpdateScheduleFragment : Fragment() {

    private lateinit var viewModel: ScheduleViewModel
    private val args by navArgs<UpdateScheduleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<UpdateScheduleFragmentBinding>(
            inflater,
            R.layout.update_schedule_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)

        val namaObat = binding.updateNamaObatFieldScheduler
        val dosisObat = binding.updateDosisObatFieldScheduler
        val namaDokter = binding.updateNamaDokterFieldScheduler
        val waktuKonsumsi = binding.updateWaktuObatFieldScheduler

        namaObat.setText(args.currentSchedule.namaObat)
        dosisObat.setText(args.currentSchedule.dosisObat)
        namaDokter.setText(args.currentSchedule.namaDokter)
        waktuKonsumsi.setText(args.currentSchedule.waktuKonsumsi)

        binding.buttonUpdateSchedule.setOnClickListener {
            updateItem(
                namaObat.text.toString(),
                dosisObat.text.toString(),
                namaDokter.text.toString(),
                waktuKonsumsi.text.toString()
            )
        }

        binding.buttonDeleteSchedule.setOnClickListener {
            deleteSchedule()
        }

        return binding.root
    }

    private fun updateItem(
        namaObat: String,
        dosisObat: String,
        namaDokter: String,
        waktuKonsumsi: String
    ) {
        if (inputCheck(namaObat, dosisObat, namaDokter, waktuKonsumsi)) {
            //create schedule object
            val updatedSchedule =
                Schedule(args.currentSchedule.id, namaObat, dosisObat, namaDokter, waktuKonsumsi)
            //update schedule
            viewModel.updateSchedule(updatedSchedule)
            Toast.makeText(requireContext(), R.string.jadwal_update_berhasil, Toast.LENGTH_LONG).show()
            //navigate back
            findNavController().navigate(R.id.action_updateScheduleFragment_to_listScheduleFragment)
        } else {
            Toast.makeText(requireContext(), R.string.jadwal_field_kurang, Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(
        namaObat: String,
        dosisObat: String,
        namaDokter: String,
        waktuKonsumsi: String
    ): Boolean {
        return !(TextUtils.isEmpty(namaObat) || TextUtils.isEmpty(dosisObat) || TextUtils.isEmpty(
            namaDokter
        ) || TextUtils.isEmpty(waktuKonsumsi))
    }

    private fun deleteSchedule() {
        var namaObat = args.currentSchedule.namaObat
        AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.dialog_allert_ya) { _, _ ->
                viewModel.deleteSchedule(args.currentSchedule)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.jadwal_delete_berhasil, namaObat),
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_updateScheduleFragment_to_listScheduleFragment)
            }
            .setNegativeButton(R.string.dialog_allert_tidak) { _, _ -> }
            .setTitle(getString(R.string.dialog_allert_title, namaObat))
            .setMessage(getString(R.string.dialog_allert_message, namaObat))
            .show()
    }

}