package com.example.resepmasakanindonesia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.example.resepmasakanindonesia.databinding.FragmentHomeBinding
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbRef = FirebaseDatabase.getInstance().reference

        // 1. Setup Resep Populer
        binding.rvResepPopuler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            onFlingListener = null
            LinearSnapHelper().attachToRecyclerView(this)
        }

        // 2. Setup Kategori Nusantara
        binding.rvKategoriNusantara.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.fabAdmin.setOnClickListener {
            startActivity(Intent(context, AdminActivity::class.java))
        }

        ambilDataSemua()
    }

    private fun ambilDataSemua() {
        if (_binding == null) return
        binding.progressBar.visibility = View.VISIBLE

        // --- SECTION 1: BERITA BUMBU ---
        dbRef.child("berita_utama").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (_binding == null) return
                val judul = snapshot.child("judul").value.toString()
                val gambar = snapshot.child("gambar_url").value.toString()

                binding.tvJudulBumbu.text = judul
                Glide.with(requireContext())
                    .load(gambar)
                    .placeholder(android.R.drawable.progress_horizontal)
                    .into(binding.imgBumbu)

                binding.cardBumbu.setOnClickListener {
                    startActivity(Intent(context, DetailBumbuActivity::class.java))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", error.message)
            }
        })

        // --- SECTION 2: 7 RESEP POPULER ---
        dbRef.child("resep_populer").limitToFirst(7).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (_binding == null) return
                val listResep = mutableListOf<Resep>()
                for (data in snapshot.children) {
                    try {
                        val item = data.getValue(Resep::class.java)
                        item?.let { listResep.add(it) }
                    } catch (e: Exception) {
                        Log.e("FIREBASE_ERROR", "Resep mismatch: ${e.message}")
                    }
                }

                binding.rvResepPopuler.adapter = ResepAdapter(listResep) { resep ->
                    val intent = Intent(context, DetailResepActivity::class.java)
                    intent.putExtra("DATA_RESEP", resep)
                    startActivity(intent)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", error.message)
            }
        })

        // --- SECTION 3: KATEGORI NUSANTARA ---
        // Perbaikan error baris 106 & 125 (Struktur interface & tanda kurung)
        dbRef.child("kategori_nusantara").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (_binding == null) return
                val listKat = mutableListOf<Kategori>()

                for (data in snapshot.children) {
                    try {
                        val kat = data.getValue(Kategori::class.java)
                        kat?.let { listKat.add(it) }
                    } catch (e: Exception) {
                        Log.e("FIREBASE_ERROR", "Kategori mismatch: ${e.message}")
                    }
                }

                binding.rvKategoriNusantara.adapter = KategoriNusantaraAdapter(listKat) { kat ->
                    val intent = Intent(context, DaftarOlahanActivity::class.java)
                    intent.putExtra("KATEGORI_NAMA", kat.nama)
                    startActivity(intent)
                }

                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                if (_binding == null) return
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}