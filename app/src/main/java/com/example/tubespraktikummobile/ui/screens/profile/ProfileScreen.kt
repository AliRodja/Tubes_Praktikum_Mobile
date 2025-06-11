// di ui/screens/profile/ProfileScreen.kt
package com.example.tubespraktikummobile.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tubespraktikummobile.ui.navigation.Screen
import com.example.tubespraktikummobile.ui.screens.home.ErrorScreen
import com.example.tubespraktikummobile.ui.screens.home.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    // Ambil state dari ViewModel. val uiState by profileViewModel.uiState akan error
    // karena kita tidak menggunakan delegate. Cukup seperti ini:
    val uiState = profileViewModel.uiState
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Listener untuk event dari ViewModel
    LaunchedEffect(key1 = true) {
        profileViewModel.eventFlow.collect { event ->
            when (event) {
                is ProfileEvent.LogoutSuccess -> {
                    // Navigasi ke login dan bersihkan semua layar sebelumnya
                    Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    // Tampilkan dialog konfirmasi jika showLogoutDialog true
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Konfirmasi Logout") },
            text = { Text("Apakah Anda yakin ingin keluar?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        profileViewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Yakin, Keluar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil Pengguna") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        // ===================================================
        // PERUBAHAN UTAMA ADA DI SINI
        // ===================================================
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is ProfileUiState.Loading -> {
                    LoadingScreen()
                }
                is ProfileUiState.Success -> {
                    // Jika sukses, tampilkan data asli pengguna
                    val user = state.user
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileInfoRow(icon = Icons.Default.Person, label = "Nama Pengguna", value = user.namaPengguna ?: "-")
                        ProfileInfoRow(icon = Icons.Default.Email, label = "Email", value = user.email ?: "-")
                        ProfileInfoRow(icon = Icons.Default.Phone, label = "Nomor HP", value = user.nomorHp ?: "-")
                        Spacer(modifier = Modifier.weight(1f)) // Mendorong tombol ke bawah
                        Button(
                            onClick = { showLogoutDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Logout")
                        }
                    }
                }
                is ProfileUiState.Error -> {
                    // Jika error, tampilkan pesan error
                    ErrorScreen(message = "Gagal memuat data profil.", onRetry = {
                        // TODO: Tambahkan fungsi retry di ViewModel jika perlu
                    })
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        }
    }
}