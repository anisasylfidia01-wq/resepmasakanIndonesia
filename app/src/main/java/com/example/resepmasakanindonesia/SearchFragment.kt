package com.example.resepmasakanindonesia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.resepmasakanindonesia.databinding.FragmentSearchBinding
import com.google.firebase.database.FirebaseDatabase

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Logika Klik Tombol Cari
        binding.btnCari.setOnClickListener {
            val query = binding.etCariResep.text.toString().trim()

            if (query.isNotEmpty()) {
                cariResepDiFirebase(query)
            } else {
                Toast.makeText(requireContext(), "Silahkan ketik nama resep dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun cariResepDiFirebase(namaCari: String) {
        // Mengakses node 'resep_populer' di Firebase Realtime Database
        val dbRef = FirebaseDatabase.getInstance().getReference("resep_populer")

        dbRef.get().addOnSuccessListener { snapshot ->
            var ditemukan = false

            if (snapshot.exists()) {
                for (resepSnapshot in snapshot.children) {
                    val resep = resepSnapshot.getValue(Resep::class.java)

                    // Cek apakah nama resep mengandung kata kunci yang diketik (Abaikan huruf besar/kecil)
                    if (resep?.nama?.contains(namaCari, ignoreCase = true) == true) {
                        ditemukan = true

                        // Jika ditemukan, langsung pindah ke DetailResepActivity
                        val intent = Intent(requireContext(), DetailResepActivity::class.java)
                        intent.putExtra("DATA_RESEP", resep)
                        startActivity(intent)
                        break // Berhenti mencari setelah menemukan satu hasil yang cocok
                    }
                }

                if (!ditemukan) {
                    Toast.makeText(requireContext(), "Resep '$namaCari' tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Gagal terhubung ke database", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}