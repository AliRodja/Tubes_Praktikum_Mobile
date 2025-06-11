// di ui/main/MainViewModel.kt
package com.example.tubespraktikummobile.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubespraktikummobile.MyApplication
import com.example.tubespraktikummobile.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // Ambil session manager dari Application class
    private val sessionManager = MyApplication.instance.sessionManager

    // State untuk menandakan apakah kita sedang mengecek token
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    // State untuk menentukan layar awal
    private val _startDestination = MutableStateFlow(Screen.Login.route)
    val startDestination = _startDestination.asStateFlow()

    init {
        // Saat ViewModel ini dibuat, langsung cek token
        viewModelScope.launch {
            // Ambil data token. .first() berarti kita hanya ambil nilainya sekali saja.
            val token = sessionManager.getAuthToken().first()

            if (!token.isNullOrBlank()) {
                // Jika token ada dan tidak kosong, layar awal adalah Home
                _startDestination.value = Screen.Home.route
            } else {
                // Jika token tidak ada, layar awal adalah Login
                _startDestination.value = Screen.Login.route
            }
            // Setelah selesai mengecek, matikan status loading
            _isLoading.value = false
        }
    }
}