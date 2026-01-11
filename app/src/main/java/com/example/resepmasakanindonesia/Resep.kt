package com.example.resepmasakanindonesia

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Resep(
    val id: String? = null,
    val nama: String? = null,
    val kategori: String? = null,
    val gambar: String? = null,
    val bahan: String? = null,
    val alat: String? = null,
    val langkah: String? = null
) : Parcelable