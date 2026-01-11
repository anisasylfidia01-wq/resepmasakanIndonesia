package com.example.resepmasakanindonesia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.resepmasakanindonesia.databinding.ItemResepBinding

class ResepAdapter(
    private val listResep: List<Resep>,
    private val onItemClick: (Resep) -> Unit
) : RecyclerView.Adapter<ResepAdapter.ResepViewHolder>() {

    // Menggunakan ViewBinding untuk performa yang lebih baik
    class ResepViewHolder(val binding: ItemResepBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResepViewHolder {
        val binding = ItemResepBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResepViewHolder, position: Int) {
        val resep = listResep[position]

        // 1. Menampilkan Nama Resep
        holder.binding.tvNamaResep.text = resep.nama

        // 2. Memuat Gambar dari Firebase URL menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(resep.gambar) // URL gambar dari Firebase
            .placeholder(android.R.drawable.progress_horizontal) // Indikator loading
            .error(android.R.drawable.stat_notify_error) // Gambar jika error
            .centerCrop()
            .into(holder.binding.imgResep)

        // 3. Logika Klik untuk mengirim data ke DetailResepActivity
        holder.itemView.setOnClickListener {
            onItemClick(resep)
        }
    }

    override fun getItemCount(): Int = listResep.size
}