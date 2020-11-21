package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.AddScheduleFragmentBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Schedule
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.service.AlarmService
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.add_schedule_fragment.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*


class AddSchedulerFragment : Fragment() {

    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<AddScheduleFragmentBinding>(
            inflater,
            R.layout.add_schedule_fragment,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)

        binding.buttonAddSchedule.setOnClickListener {
            var namaObat = binding.namaObatFieldScheduler.text.toString()
            var dosisObat = binding.dosisObatFieldScheduler.text.toString()
            var namaDokter = binding.namaDokterFieldScheduler.text.toString()
            var waktuKonsumsi = binding.waktuObatFieldScheduler.text.toString()
            if (insertDataToDatabase(namaObat, dosisObat, namaDokter, waktuKonsumsi)) {
                clearEditText(it)
            }
        }

        binding.buttonWaktuObat.setOnClickListener {
            var alarmService = AlarmService(requireContext())
            setAlarm {
                alarmService.setRepetitiveAlarm(it)
            }
        }

        return binding.root
    }

    private fun insertDataToDatabase(
        namaObat: String,
        dosisObat: String,
        namaDokter: String,
        waktuKonsumsi: String
    ): Boolean {
        if (inputCheck(namaObat, dosisObat, namaDokter, waktuKonsumsi)) {
            //create schedule
            val schedule = Schedule(0, namaObat, dosisObat, namaDokter, waktuKonsumsi)
            //add schedule to database
            viewModel.addSchedule(schedule)

            Toast.makeText(requireContext(), R.string.jadwal_berhasil, Toast.LENGTH_LONG)
                .show()
            return true
        } else {
            Toast.makeText(requireContext(), R.string.jadwal_field_kurang, Toast.LENGTH_LONG).show()
            return false
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
    }

    fun clearEditText(view: View) {
        nama_obat_field_scheduler.text.clear()
        dosis_obat_field_scheduler.text.clear()
        nama_dokter_field_scheduler.text.clear()
        waktu_obat_field_scheduler.setText(R.string.waktu_placeholder)
    }

    @Suppress("DEPRECATION")
    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            TimePickerDialog(
                context,
                0,
                { _, hour, minute ->
                    this.set(Calendar.HOUR_OF_DAY, hour)
                    this.set(Calendar.MINUTE, minute)
                    val time = Time(hour, minute, 0)
                    val simpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
                    val text: String = simpleDateFormat.format(time)
                    waktu_obat_field_scheduler.setText(text)
                    callback(this.timeInMillis)
                },
                this.get(Calendar.HOUR_OF_DAY),
                this.get(Calendar.MINUTE),
                false
            ).show()

        }
    }
}