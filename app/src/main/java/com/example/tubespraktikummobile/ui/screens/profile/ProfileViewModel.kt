// di ui/screens/profile/ProfileViewModel.kt
package com.example.tubespraktikummobile.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubespraktikummobile.MyApplication
import com.example.tubespraktikummobile.data.network.RetrofitInstance
import com.example.tubespraktikummobile.data.network.UserProfile
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// --- STATE BARU UNTUK MENAMPUNG DATA PROFIL ---
sealed interface ProfileUiState {
    data class Success(val user: UserProfile) : ProfileUiState
    object Error : ProfileUiState
    object Loading : ProfileUiState
}

sealed interface ProfileEvent {
    object LogoutSuccess : ProfileEvent
}

class ProfileViewModel : ViewModel() {
    private val sessionManager = MyApplication.instance.sessionManager
    private val apiService = RetrofitInstance.api

    var uiState: ProfileUiState by mutableStateOf(ProfileUiState.Loading)
        private set

    private val _eventFlow = MutableSharedFlow<ProfileEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // Langsung ambil data profil saat ViewModel dibuat
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        uiState = ProfileUiState.Loading
        viewModelScope.launch {
            // Ambil token dulu dari DataStore
            val token = sessionManager.getAuthToken().first()
            if (token.isNullOrBlank()) {
                uiState = ProfileUiState.Error
                return@launch
            }

            uiState = try {
                // Panggil API dengan token
                val response = apiService.getMyProfile("Bearer $token")
                if (response.data != null) {
                    ProfileUiState.Success(response.data)
                } else {
                    ProfileUiState.Error
                }
            } catch (e: Exception) {
                ProfileUiState.Error
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.logout()
            _eventFlow.emit(ProfileEvent.LogoutSuccess)
        }
    }
}