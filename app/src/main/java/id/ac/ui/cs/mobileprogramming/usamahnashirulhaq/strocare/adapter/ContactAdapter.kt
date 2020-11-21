package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.model.Contact

class ContactAdapter() :
    RecyclerView.Adapter<ContactAdapter.contactViewHolder>() {

    private var contactList = emptyList<Contact>()

    class contactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaKontak: TextView = itemView.findViewById(R.id.tv_nama_kontak)
        val nomorKontak: TextView = itemView.findViewById(R.id.tv_nomor_kontak)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_contact_frame,
            parent, false
        )

        return contactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: contactViewHolder, position: Int) {
        val currentItem = contactList[position]

        holder.namaKontak.text = currentItem.nama
        holder.nomorKontak.text = currentItem.nomorTelepon

    }

    override fun getItemCount() = contactList.size

    fun setData(contact: List<Contact>){
        this.contactList = contact
        notifyDataSetChanged()
    }

}