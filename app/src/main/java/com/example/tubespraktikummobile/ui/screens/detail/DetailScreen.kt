// di ui/screens/detail/DetailScreen.kt
package com.example.tubespraktikummobile.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tubespraktikummobile.data.Gedung
import com.example.tubespraktikummobile.ui.screens.home.ErrorScreen
import com.example.tubespraktikummobile.ui.screens.home.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    detailViewModel: DetailViewModel = viewModel()
) {
    val uiState by detailViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Gedung") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = uiState) {
                is DetailUiState.Loading -> LoadingScreen()
                is DetailUiState.Success -> DetailContent(gedung = state.gedung)
                is DetailUiState.Error -> ErrorScreen(message = "Gagal memuat data.", onRetry = { /* TODO */ })
            }
        }
    }
}

@Composable
fun DetailContent(gedung: Gedung) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = gedung.imageUrl,
            contentDescription = "Gambar ${gedung.nama}",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = gedung.nama ?: "Nama Gedung",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(icon = Icons.Default.LocationOn, text = gedung.lokasi ?: "Lokasi tidak tersedia")
            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Detail", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(icon = Icons.Default.People, text = "Kapasitas: ${gedung.kapasitas ?: 0} orang")
            InfoRow(icon = Icons.Default.Star, text = "Penilaian: ${gedung.penilaian ?: "N/A"}")
            InfoRow(icon = Icons.Default.List, text = "Fasilitas: ${gedung.fasilitas ?: "Tidak ada info"}")
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /* TODO: Navigasi ke halaman booking */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Pesan Sekarang (Rp ${gedung.hargaPerHari?.toInt() ?: 0}/hari)", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}