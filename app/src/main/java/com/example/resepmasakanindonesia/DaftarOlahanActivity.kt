package com.example.resepmasakanindonesia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.resepmasakanindonesia.databinding.ActivityDaftarOlahanBinding
import com.google.firebase.database.*

class DaftarOlahanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarOlahanBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarOlahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Ambil Nama Kategori dari Intent
        val kategoriNama = intent.getStringExtra("KATEGORI_NAMA") ?: ""
        binding.tvHeader.text = kategoriNama

        binding.rvOlahan.layoutManager = GridLayoutManager(this, 2)

        // 2. Cari data berdasarkan kategori
        fetchOlahanBerdasarkanKategori(kategoriNama)

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun fetchOlahanBerdasarkanKategori(namaKat: String) {
        binding.progressBar.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("kategori_nusantara")

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listOlahan = mutableListOf<Resep>()

                // Mencari kategori yang namanya cocok
                for (katSnapshot in snapshot.children) {
                    val namaDiDb = katSnapshot.child("nama").value.toString()

                    if (namaDiDb == namaKat) {
                        // Ambil isi dari node "daftar_olahan"
                        val olahanSnapshot = katSnapshot.child("daftar_olahan")
                        for (resepData in olahanSnapshot.children) {
                            val resep = resepData.getValue(Resep::class.java)
                            resep?.let { listOlahan.add(it) }
                        }
                        break
                    }
                }

                if (listOlahan.isEmpty()) {
                    Toast.makeText(this@DaftarOlahanActivity, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                }

                // Set Adapter
                binding.rvOlahan.adapter = ResepAdapter(listOlahan) { resep ->
                    val intent = Intent(this@DaftarOlahanActivity, DetailResepActivity::class.java)
                    intent.putExtra("DATA_RESEP", resep)
                    startActivity(intent)
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}