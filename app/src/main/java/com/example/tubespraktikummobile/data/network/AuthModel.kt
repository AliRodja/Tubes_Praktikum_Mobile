// di data/network/AuthModel.kt
package com.example.tubespraktikummobile.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("nama_pengguna")
    val namaPengguna: String,
    val email: String,
    @SerialName("kata_sandi")
    val kataSandi: String,
    @SerialName("nomor_hp")
    val nomorHp: String? // Jadikan nullable
)

@Serializable
data class LoginRequest(
    val email: String,
    @SerialName("kata_sandi")
    val kataSandi: String
)

@Serializable
data class AuthResponse(
    val status: String?, // Jadikan nullable
    val message: String? // Jadikan nullable
)

@Serializable
data class LoginResponse(
    val status: String?, // Jadikan nullable
    val message: String?, // Jadikan nullable
    val token: String? // Jadikan nullable
)

@Serializable
data class UserProfile(
    val id: Int,
    @SerialName("nama_pengguna")
    val namaPengguna: String?,
    val email: String?,
    @SerialName("nomor_hp")
    val nomorHp: String?
)

@Serializable
data class ProfileResponse(
    val status: String?,
    val data: UserProfile?
)