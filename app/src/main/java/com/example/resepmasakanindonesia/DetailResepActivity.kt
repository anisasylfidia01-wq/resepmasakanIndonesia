package com.example.resepmasakanindonesia

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit // Tambahkan ini untuk SharedPreferences.edit yang lebih ringkas
import com.bumptech.glide.Glide
import com.example.resepmasakanindonesia.databinding.ActivityDetailResepBinding

class DetailResepActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailResepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailResepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Mengambil data Parcelable (Menghilangkan warning 'Explicit type arguments')
        val dataResep = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DATA_RESEP", Resep::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("DATA_RESEP")
        }

        dataResep?.let { resep ->
            // 2. Tampilkan data ke UI
            isiDataResep(resep)

            // 3. PERBAIKAN ERROR: Berikan nilai default jika id null
            // Menggunakan operator ?: "" untuk memastikan tipenya String (bukan String?)
            cekApakahPernahDilihat(resep.id ?: "")
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun isiDataResep(resep: Resep) {
        // Gunakan nilai default jika field di Firebase kosong agar tidak muncul tulisan "null"
        binding.tvDetailNama.text = resep.nama ?: "Nama Tidak Tersedia"
        binding.tvDetailAlat.text = resep.alat ?: "-"
        binding.tvDetailBahan.text = resep.bahan ?: "-"
        binding.tvDetailLangkah.text = resep.langkah ?: "-"

        Glide.with(this)
            .load(resep.gambar)
            .placeholder(android.R.drawable.progress_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(binding.imgDetailResep)
    }

    private fun cekApakahPernahDilihat(resepId: String) {
        // Jangan jalankan logika jika ID kosong
        if (resepId.isEmpty()) return

        val sharedPref = getSharedPreferences("RiwayatResep", Context.MODE_PRIVATE)
        val sudahDilihat = sharedPref.getBoolean(resepId, false)

        if (sudahDilihat) {
            Toast.makeText(
                this,
                "Catatan: Resep ini sudah pernah Anda lihat sebelumnya.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            // Menggunakan KTX Extension SharedPreferences.edit agar lebih modern (Warning baris 71)
            sharedPref.edit {
                putBoolean(resepId, true)
            }
        }
    }
}