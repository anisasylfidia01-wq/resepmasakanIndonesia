package com.example.resepmasakanindonesia

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.resepmasakanindonesia.databinding.ActivityAdminBinding
import com.google.firebase.database.FirebaseDatabase

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    // Referensi ke Firebase Realtime Database pada node "resep_populer"
    private val dbRef = FirebaseDatabase.getInstance().getReference("resep_populer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol Simpan
        binding.btnSimpan.setOnClickListener {
            simpanResep()
        }

        // Tombol Hapus
        binding.btnHapus.setOnClickListener {
            hapusResep()
        }
    }

    private fun simpanResep() {
        // Ambil data dari EditText
        val nama = binding.etNamaResep.text.toString()
        val gambar = binding.etUrlGambar.text.toString()
        val bahan = binding.etBahan.text.toString()
        val alat = binding.etAlat.text.toString()
        val langkah = binding.etLangkah.text.toString()

        // Validasi input kosong
        if (nama.isEmpty() || bahan.isEmpty() || langkah.isEmpty()) {
            Toast.makeText(this, "Nama, Bahan, dan Langkah wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        // Buat ID unik secara otomatis di Firebase
        val id = dbRef.push().key!!

        // Membuat objek resep
        val resep = Resep(id, nama, gambar, alat, bahan, langkah)

        // Simpan ke Firebase
        dbRef.child(id).setValue(resep).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Memanggil fungsi notifikasi gabungan
                tampilkanNotifikasiBerhasil(nama)

                Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                clearForm()
            } else {
                Toast.makeText(this, "Gagal menyimpan: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hapusResep() {
        val nama = binding.etNamaResep.text.toString()

        if (nama.isEmpty()) {
            Toast.makeText(this, "Masukkan Nama Masakan untuk menghapus!", Toast.LENGTH_SHORT).show()
            return
        }

        // Mencari resep berdasarkan nama untuk mendapatkan ID-nya, lalu menghapus
        dbRef.orderByChild("nama").equalTo(nama).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                for (resepSnapshot in snapshot.children) {
                    resepSnapshot.ref.removeValue().addOnSuccessListener {
                        Toast.makeText(this, "Resep $nama berhasil dihapus", Toast.LENGTH_SHORT).show()
                        clearForm()
                    }
                }
            } else {
                Toast.makeText(this, "Resep tidak ditemukan!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearForm() {
        binding.apply {
            etNamaResep.text.clear()
            etUrlGambar.text.clear()
            etBahan.text.clear()
            etAlat.text.clear()
            etLangkah.text.clear()
        }
    }

    // Fungsi Notifikasi Gabungan (Syarat CPMK 3)
    private fun tampilkanNotifikasiBerhasil(namaResep: String) {
        val channelId = "resep_channel"
        val notificationId = 1

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Membuat Channel untuk Android Oreo ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notifikasi Resep",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Pastikan R.drawable.ic_check ada, atau gunakan icon sistem ini
            .setContentTitle("Berhasil!")
            .setContentText("Resep '$namaResep' berhasil ditambahkan ke koleksi.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        manager.notify(notificationId, builder.build())
    }
}