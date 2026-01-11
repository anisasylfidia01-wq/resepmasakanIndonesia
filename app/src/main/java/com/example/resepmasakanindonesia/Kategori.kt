package com.example.resepmasakanindonesia

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kategori(
    val id: String? = null, // Ubah dari Int ke String
    val nama: String? = null,
    val gambar: String? = null
) : Parcelable