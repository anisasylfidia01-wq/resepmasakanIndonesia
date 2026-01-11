package com.example.resepmasakanindonesia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resepmasakanindonesia.databinding.ActivityDetailBumbuBinding

class DetailBumbuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBumbuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBumbuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listBumbu = arrayListOf(
            Bumbu("Bawang Merah", R.drawable.pngtree_vibrant_red_onion_halved_showing_layered_texture_and_freshness_studio_shot_image_20906046, "Bumbu dasar untuk memberikan rasa gurih dan aroma harum."),
            Bumbu("Bawang Putih", R.drawable.bawangputih_hero_width_800_format_webp, "Bumbu inti yang memiliki khasiat antioksidan dan aroma kuat."),
            Bumbu("Cabai Merah", R.drawable.hvpc3op820231101151152_jpeg, "Memberikan rasa pedas dan warna merah menyala pada masakan."),
            Bumbu("Jahe", R.drawable.jahe_emprit_width_800_format_webp, "Memberikan rasa hangat dan menghilangkan aroma amis pada daging."),
            Bumbu("Kunyit", R.drawable.kunyit_mempunyai_manfaat_mencegah_kanker_dan_diabetes_karena_kjbf, "Pemberi warna kuning alami dan aroma khas tanah."),
            Bumbu("Lengkuas", R.drawable.__1753657638_1, "Memberikan aroma segar pada sayuran berkuah."),
            Bumbu("Kencur", R.drawable.xx_manfaat_kencur_salah_satu_baha_width_800_format_webp, "Bumbu utama untuk seblak dan urap, aromanya sangat kuat."),
            Bumbu("Serai", R.drawable.lemongrass_on_a_wooden_background_width_800_format_webp, "Batang yang memberikan aroma sitrus segar pada masakan."),
            Bumbu("Daun Salam", R.drawable.header_35, "Memberikan aroma khas yang menenangkan pada nasi atau gulai."),
            Bumbu("Ketumbar", R.drawable.id_11134207_7r98z_ll47sezbytzg9d_tn, "Bentuk biji kecil yang memberikan rasa gurih pada gorengan."),
            Bumbu("Lada Putih", R.drawable.dried_white_pepper, "Memberikan rasa pedas hangat dan aroma tajam."),
            Bumbu("Kemiri", R.drawable.manfaat_kemiri_width_800_format_webp, "Digunakan untuk mengentalkan kuah dan menambah rasa gurih."),
            Bumbu("Pala", R.drawable.buah_pala_ini_kandungan_nutrisi_dan_manfaatnya_untuk_kesehatan_1_jpg, "Biasanya digunakan untuk masakan bersantan atau sup."),
            Bumbu("Cengkeh", R.drawable.pngtree_cloves_spice_close_up_high_quality_png_image_17500989, "Rempah aromatik tajam untuk masakan seperti semur."),
            Bumbu("Kayu Manis", R.drawable.cinnamon_information_1024x1024, "Memberikan aroma manis kayu yang eksotis."),
            Bumbu("Asam Jawa", R.drawable.mengenal_berbagai_manfaat_asam_jawa_untuk_kesehatan_jpg, "Memberikan rasa asam segar pada sayur asem atau pindang."),
            Bumbu("Terasi", R.drawable.gambar_sampul_1750148013, "Udang fermentasi yang menjadi kunci kelezatan sambal."),
            Bumbu("Jinten", R.drawable.cumin, "Memberikan aroma tajam yang khas pada opor dan gulai."),
            Bumbu("Kapulaga", R.drawable.manfaat_kesehatan_kapulaga_2_width_800_format_webp, "Rempah mahal yang memberikan aroma harum bunga."),
            Bumbu("Bunga Lawang", R.drawable.mitos_atau_fakta_bunga_lawang_mampu_mencegah_rematik__jpg, "Berbentuk bintang, pemberi aroma sedap pada sup."),
            Bumbu("Daun Jeruk", R.drawable.x_tips_memilih_daun_jeruk_yang_tepat_sebagai_bumbu_masakan_jpg, "Menghilangkan bau amis dan memberi aroma jeruk segar."),
            Bumbu("Keluak", R.drawable.sg_11134253_7rdvv_mcwsthjryiws46_tn, "Bahan utama rawon yang memberikan warna hitam alami."),
            Bumbu("Adas Manis", R.drawable._ddd36425ad50ecfb97e26a17e9fdee6_png_960x960q80_png_, "Rempah yang mirip jinten namun lebih manis."),
            Bumbu("Wijen", R.drawable.id_11134207_7qul9_lkdejd1xbayq77_tn, "Biji kecil pemberi aroma kacang pada masakan oriental."),
            Bumbu("Andaliman", R.drawable._cb0ca2c4a5d6629158ff21ac39cbf73_tn, "Merica batak yang memberikan sensasi getir di lidah."),
            Bumbu("Kunci", R.drawable.tak_hanya_bumbu_makanan_ketahui_x_manfaat_temu_kunci_jpg, "Bumbu khusus untuk masakan sayur bening agar lebih segar."),
            Bumbu("Kluwak", R.drawable.apa_itu_kluwek_width_800_format_webp, "Pemberi rasa gurih dan warna hitam pada masakan."),
            Bumbu("Kemangi", R.drawable.ini_4_manfaat_daun_kemangi_yang_jarang_diketahui_halodoc_jpg, "Daun dengan aroma mint segar untuk pepes dan lalapan."),
            Bumbu("Kucai", R.drawable._51839563_232541395247492_4390018_width_800_format_webp, "Daun mirip bawang merah namun lebih tipis dan halus."),
            Bumbu("Bawang Bombay", R.drawable._20870_8_6_2021_23_11_21, "Bawang besar yang memberikan rasa manis saat ditumis.")
        )

        setupRecyclerView(listBumbu)
    }

    private fun setupRecyclerView(list: List<Bumbu>) {
        binding.rvBumbu.layoutManager = LinearLayoutManager(this)
        // Gunakan adapter khusus bumbu (Anda perlu membuat BumbuAdapter)
        binding.rvBumbu.adapter = BumbuAdapter(list)
    }
}