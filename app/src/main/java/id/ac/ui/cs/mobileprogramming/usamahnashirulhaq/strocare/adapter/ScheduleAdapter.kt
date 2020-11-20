package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment.ListScheduleFragmentDirections
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Schedule
import kotlinx.android.synthetic.main.list_schedule_frame.view.*

class ScheduleAdapter() :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

     private var scheduleList = emptyList<Schedule>()

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaObat: TextView = itemView.findViewById(R.id.tv_nama_obat)
        val dosisObat: TextView = itemView.findViewById(R.id.tv_dosis_obat)
        val namaDokter: TextView = itemView.findViewById(R.id.tv_nama_dokter)
        val jadwalObat: TextView = itemView.findViewById(R.id.tv_jadwal_obat)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_schedule_frame,
            parent, false
        )

        return ScheduleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val currentItem = scheduleList[position]

        holder.namaObat.text = currentItem.namaObat
        holder.dosisObat.text = currentItem.dosisObat
        holder.namaDokter.text = currentItem.namaDokter
        holder.jadwalObat.text = currentItem.waktuKonsumsi

        holder.itemView.schedule_card.setOnClickListener {
            val action = ListScheduleFragmentDirections.actionListScheduleFragmentToUpdateScheduleFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = scheduleList.size

    fun setData(schedule: List<Schedule>){
        this.scheduleList = schedule
        notifyDataSetChanged()
    }

}