// di dalam data/network/RetrofitInstance.kt
package com.example.tubespraktikummobile.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitInstance {
    // PENTING: Gunakan IP ini untuk akses localhost dari Emulator Android
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    // Konfigurasi JSON agar lebih toleran
    private val json = Json {
        ignoreUnknownKeys = true // Wajib ada jika JSON punya field yg tidak kita definisikan
    }

    // Membuat instance Retrofit yang akan kita gunakan di seluruh aplikasi
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}