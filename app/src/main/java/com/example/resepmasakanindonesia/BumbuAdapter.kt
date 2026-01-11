package com.example.resepmasakanindonesia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BumbuAdapter(private val listBumbu: List<Bumbu>) :
    RecyclerView.Adapter<BumbuAdapter.BumbuViewHolder>() {

    class BumbuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgBumbu: ImageView = view.findViewById(R.id.imgBumbu)
        val tvNama: TextView = view.findViewById(R.id.tvNamaBumbu)
        val tvDeskripsi: TextView = view.findViewById(R.id.tvDeskripsiBumbu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BumbuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bumbu, parent, false)
        return BumbuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BumbuViewHolder, position: Int) {
        val bumbu = listBumbu[position]
        holder.tvNama.text = bumbu.nama
        holder.tvDeskripsi.text = bumbu.penjelasan
        holder.imgBumbu.setImageResource(bumbu.gambar)
    }

    override fun getItemCount(): Int = listBumbu.size
}