// di dalam ui/screens/home/GedungUiState.kt
package com.example.tubespraktikummobile.ui.screens.home

import com.example.tubespraktikummobile.data.Gedung

sealed interface GedungUiState {
    object Loading : GedungUiState
    data class Success(val gedungList: List<Gedung>) : GedungUiState
    data class Error(val message: String) : GedungUiState
}