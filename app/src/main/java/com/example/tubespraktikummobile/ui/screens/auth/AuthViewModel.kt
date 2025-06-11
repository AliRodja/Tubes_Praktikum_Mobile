// di ui/screens/auth/AuthViewModel.kt
package com.example.tubespraktikummobile.ui.screens.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubespraktikummobile.MyApplication // <-- 1. IMPORT MyApplication
import com.example.tubespraktikummobile.data.network.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// ... (Class AuthState, AuthUiState, dan AuthEvent tidak berubah) ...
class AuthState {
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
}
sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
}
sealed interface AuthEvent {
    data class LoginSuccess(val token: String) : AuthEvent
    object RegisterSuccess : AuthEvent
    data class Error(val message: String) : AuthEvent
}


class AuthViewModel : ViewModel() {
    val authState = AuthState()
    var authUiState: AuthUiState by mutableStateOf(AuthUiState.Idle)
        private set

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val apiService = RetrofitInstance.api

    // 2. AMBIL INSTANCE SESSION MANAGER DARI MyApplication
    private val sessionManager = MyApplication.instance.sessionManager

    fun onLoginClicked() {
        viewModelScope.launch {
            authUiState = AuthUiState.Loading
            try {
                val request = LoginRequest(
                    email = authState.email,
                    kataSandi = authState.password
                )
                val response = apiService.loginUser(request)

                if (response.token != null) {
                    // 3. SIMPAN TOKEN MENGGUNAKAN SESSION MANAGER
                    sessionManager.saveAuthToken(response.token)

                    // Setelah token disimpan, baru kirim event sukses
                    _eventFlow.emit(AuthEvent.LoginSuccess(response.token))
                } else {
                    _eventFlow.emit(AuthEvent.Error("Login Gagal: Tidak menerima token dari server."))
                }

            } catch (e: HttpException) {
                Log.e("AuthViewModel", "HttpException in Login", e)
                _eventFlow.emit(AuthEvent.Error("Email atau kata sandi salah."))
            } catch (e: IOException) {
                Log.e("AuthViewModel", "IOException in Login", e)
                _eventFlow.emit(AuthEvent.Error("Tidak dapat terhubung ke server. Periksa koneksi Anda."))
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Exception in Login", e)
                _eventFlow.emit(AuthEvent.Error(e.message ?: "Terjadi kesalahan tidak diketahui."))
            } finally {
                authUiState = AuthUiState.Idle
            }
        }
    }

    // Fungsi onRegisterClicked tidak perlu diubah
    fun onRegisterClicked() {
        viewModelScope.launch {
            authUiState = AuthUiState.Loading
            try {
                val request = RegisterRequest(
                    namaPengguna = authState.username,
                    email = authState.email,
                    kataSandi = authState.password,
                    nomorHp = authState.phone
                )
                apiService.registerUser(request)
                _eventFlow.emit(AuthEvent.RegisterSuccess)
            } catch (e: HttpException) {
                Log.e("AuthViewModel", "HttpException in Register", e)
                _eventFlow.emit(AuthEvent.Error("Email sudah terdaftar."))
            } catch (e: IOException) {
                Log.e("AuthViewModel", "IOException in Register", e)
                _eventFlow.emit(AuthEvent.Error("Tidak dapat terhubung ke server. Periksa koneksi Anda."))
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Exception in Register", e)
                _eventFlow.emit(AuthEvent.Error(e.message ?: "Terjadi kesalahan tidak diketahui."))
            } finally {
                authUiState = AuthUiState.Idle
            }
        }
    }
}