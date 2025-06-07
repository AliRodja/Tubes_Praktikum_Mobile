package com.example.tubespraktikummobile.ui.screens.home

// di dalam file ui/screens/home/HomeViewModel.kt

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    // Data yang akan "didengarkan" oleh UI (View)
    private val _uiState = MutableStateFlow("Halo dari ViewModel!")
    val uiState = _uiState.asStateFlow() // Versi read-only untuk UI

    // Fungsi yang bisa dipanggil oleh UI
    fun onButtonClicked() {
        _uiState.value = "Tombol sudah diklik! ✅"
    }
}