// di ui/screens/detail/DetailViewModel.kt
package com.example.tubespraktikummobile.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubespraktikummobile.data.Gedung
import com.example.tubespraktikummobile.data.GedungRepository
import com.example.tubespraktikummobile.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// Definisikan state untuk UI Halaman Detail
sealed interface DetailUiState {
    data class Success(val gedung: Gedung) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // State untuk UI
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    // Ambil ID gedung dari argumen navigasi
    private val gedungId: Int = checkNotNull(savedStateHandle["gedungId"])

    private val gedungRepository: GedungRepository

    init {
        val apiService = RetrofitInstance.api
        gedungRepository = GedungRepository(apiService)
        // Langsung ambil data detail saat ViewModel dibuat
        fetchGedungDetails()
    }

    private fun fetchGedungDetails() {
        _uiState.value = DetailUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                val response = gedungRepository.getGedungById(gedungId)
                if (response.data != null) {
                    DetailUiState.Success(response.data)
                } else {
                    DetailUiState.Error
                }
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }
}