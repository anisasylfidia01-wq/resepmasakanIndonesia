package com.example.resepmasakanindonesia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.resepmasakanindonesia.databinding.ItemKategoriNusantaraBinding

class KategoriNusantaraAdapter(
    private val listKategori: List<Kategori>,
    private val onItemClick: (Kategori) -> Unit
) : RecyclerView.Adapter<KategoriNusantaraAdapter.ViewHolder>() {

    // Gunakan class binding yang sesuai dengan file item_kategori_nusantara.xml
    class ViewHolder(val binding: ItemKategoriNusantaraBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemKategoriNusantaraBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kategori = listKategori[position]

        // Pastikan ID tvNamaKategori dan imgKategori ada di XML
        holder.binding.tvNamaKategori.text = kategori.nama

        Glide.with(holder.itemView.context)
            .load(kategori.gambar)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.binding.imgKategori)

        // Callback klik dikirimkan kembali ke HomeFragment
        holder.itemView.setOnClickListener {
            onItemClick(kategori)
        }
    }

    override fun getItemCount(): Int = listKategori.size
}