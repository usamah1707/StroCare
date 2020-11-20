package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.fragment.ListReportFragmentDirections
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Report
import kotlinx.android.synthetic.main.activity_report.view.*
import kotlinx.android.synthetic.main.list_report_frame.view.*

class ReportAdapter() :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    private var ReportList = emptyList<Report>()

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tanggalLaporan: TextView = itemView.findViewById(R.id.tv_tanggal_laporan)
        val kesimpulanLaporan: TextView = itemView.findViewById(R.id.tv_kesimpulan_laporan)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_report_frame,
            parent, false
        )

        return ReportViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val currentItem = ReportList[position]

        holder.tanggalLaporan.text = currentItem.tanggalLaporan
        holder.kesimpulanLaporan.text = currentItem.kesimpulanLaporan

        holder.itemView.report_card.setOnClickListener {
            val action = ListReportFragmentDirections.actionListReportFragmentToDetailReportFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = ReportList.size

    fun setData(report: List<Report>){
        this.ReportList = report
        notifyDataSetChanged()
    }

}