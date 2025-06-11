// di dalam data/GedungRepository.kt
package com.example.tubespraktikummobile.data

import com.example.tubespraktikummobile.data.network.ApiService
import com.example.tubespraktikummobile.data.GedungDetailApiResponse

class GedungRepository(private val apiService: ApiService) {

    // suspend fun menandakan fungsi ini adalah fungsi coroutine
    // yang bisa di-pause dan dilanjutkan, cocok untuk operasi jaringan.
    suspend fun getAllGedung(): GedungApiResponse {
        // Memanggil fungsi yang ada di ApiService
        return apiService.getAllGedung()
    }

    suspend fun getGedungById(id: Int): GedungDetailApiResponse {
        return apiService.getGedungById(id)
    }
}