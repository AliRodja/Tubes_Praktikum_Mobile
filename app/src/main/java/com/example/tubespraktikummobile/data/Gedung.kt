// di data/Gedung.kt
package com.example.tubespraktikummobile.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gedung(
    @SerialName("id")
    val id: Int,

    @SerialName("nama")
    val nama: String?,

    @SerialName("lokasi")
    val lokasi: String?,

    @SerialName("kapasitas")
    val kapasitas: Int?,

    @SerialName("penilaian")
    val penilaian: Double?,

    @SerialName("harga_per_hari")
    val hargaPerHari: Double?,

    @SerialName("image_url")
    val imageUrl: String?,

    // ===================================
    // TAMBAHKAN PROPERTI INI
    // ===================================
    @SerialName("fasilitas")
    val fasilitas: String?
)

// --- Data class di bawah ini biarkan saja seperti adanya ---

@Serializable
data class GedungApiResponse(
    @SerialName("status")
    val status: String?,

    @SerialName("data")
    val data: List<Gedung>?
)

@Serializable
data class GedungDetailApiResponse(
    @SerialName("status")
    val status: String?,

    @SerialName("data")
    val data: Gedung?
)