// di ui/screens/signup/SignUpScreen.kt
package com.example.tubespraktikummobile.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tubespraktikummobile.ui.navigation.Screen
import com.example.tubespraktikummobile.ui.screens.auth.AuthEvent
import com.example.tubespraktikummobile.ui.screens.auth.AuthUiState
import com.example.tubespraktikummobile.ui.screens.auth.AuthViewModel
import com.example.tubespraktikummobile.ui.screens.login.CustomLoginTextField
import com.example.tubespraktikummobile.ui.screens.login.DarkBrown
import com.example.tubespraktikummobile.ui.screens.login.BackgroundBeige

@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val authState = authViewModel.authState
    val uiState = authViewModel.authUiState

    // LaunchedEffect untuk menangani event dari ViewModel (registrasi berhasil/gagal)
    LaunchedEffect(key1 = true) {
        authViewModel.eventFlow.collect { event ->
            when (event) {
                is AuthEvent.RegisterSuccess -> {
                    Toast.makeText(context, "Registrasi Berhasil! Silakan login.", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                is AuthEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    // --- TAMPILAN UI BARU UNTUK SIGN UP ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBeige)
    ) {
        // Banner atas (sama seperti Login Screen)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(DarkBrown),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Reservasi Gedung Serba Guna",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // Kartu Daftar di tengah
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Judul "DAFTAR"
                    Text(
                        text = "DAFTAR",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Input Fields
                    CustomLoginTextField(
                        label = "Nama Pengguna",
                        placeholder = "Masukkan nama pengguna",
                        value = authState.username,
                        onValueChange = { authState.username = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomLoginTextField(
                        label = "Email",
                        placeholder = "Masukkan alamat email",
                        value = authState.email,
                        onValueChange = { authState.email = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomLoginTextField(
                        label = "Nomor HP",
                        placeholder = "Masukkan nomor HP",
                        value = authState.phone,
                        onValueChange = { authState.phone = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomLoginTextField(
                        label = "Kata Sandi",
                        placeholder = "Buat kata sandi",
                        value = authState.password,
                        onValueChange = { authState.password = it },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    // Tombol Daftar
                    Button(
                        onClick = { authViewModel.onRegisterClicked() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkBrown),
                        enabled = uiState !is AuthUiState.Loading
                    ) {
                        if (uiState is AuthUiState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Text("Daftar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    // Teks "Sudah memiliki akun? Masuk"
                    SignInText(
                        onSignInClick = {
                            navController.popBackStack() // Kembali ke Login Screen
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

// Composable untuk teks "Sudah memiliki akun? Masuk" (mirip SignUpText di LoginScreen)
@Composable
fun SignInText(onSignInClick: () -> Unit, modifier: Modifier = Modifier) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = DarkBrown)) {
            append("Sudah memiliki akun? ")
        }
        pushStringAnnotation(tag = "SIGNIN", annotation = "signin")
        withStyle(style = SpanStyle(color = DarkBrown, fontWeight = FontWeight.Bold)) {
            append("Masuk")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "SIGNIN", start = offset, end = offset)
                .firstOrNull()?.let {
                    onSignInClick()
                }
        },
        modifier = modifier
    )
}