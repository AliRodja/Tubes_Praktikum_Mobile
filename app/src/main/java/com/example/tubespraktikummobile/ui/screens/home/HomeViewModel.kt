// di ui/screens/home/HomeViewModel.kt
package com.example.tubespraktikummobile.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubespraktikummobile.data.GedungRepository
import com.example.tubespraktikummobile.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {

    var gedungUiState: GedungUiState by mutableStateOf(GedungUiState.Loading)
        private set

    // Repository tidak perlu di-init di sini lagi jika kita menggunakan Hilt/Koin nanti,
    // tapi untuk sekarang ini sudah cukup.
    private val gedungRepository: GedungRepository

    init {
        val apiService = RetrofitInstance.api
        // Belum ada repository untuk gedung, kita buat di sini saja sementara
        // Seharusnya ada file GedungRepository.kt tersendiri
        gedungRepository = GedungRepository(apiService)
        fetchGedungList()
    }

    fun fetchGedungList() {
        gedungUiState = GedungUiState.Loading
        viewModelScope.launch {
            gedungUiState = try {
                val response = gedungRepository.getAllGedung()

                // === PERBAIKAN UTAMA ADA DI SINI ===
                GedungUiState.Success(response.data ?: emptyList())
                // ===================================

            } catch (e: IOException) {
                GedungUiState.Error("Gagal terhubung ke server. Periksa koneksi internet Anda.")
            } catch (e: Exception) {
                GedungUiState.Error(e.message ?: "Terjadi kesalahan tidak diketahui.")
            }
        }
    }
}