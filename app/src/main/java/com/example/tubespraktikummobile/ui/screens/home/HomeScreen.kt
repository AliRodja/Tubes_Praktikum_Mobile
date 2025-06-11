// di ui/screens/home/HomeScreen.kt
package com.example.tubespraktikummobile.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tubespraktikummobile.data.Gedung
import com.example.tubespraktikummobile.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState = homeViewModel.gedungUiState

    when (uiState) {
        is GedungUiState.Loading -> {
            LoadingScreen()
        }
        is GedungUiState.Success -> {
            MainScreen(
                navController = navController,
                gedungList = uiState.gedungList ?: emptyList() // Memberi list kosong jika data null
            )
        }
        is GedungUiState.Error -> {
            ErrorScreen(message = uiState.message, onRetry = {
                homeViewModel.fetchGedungList()
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    gedungList: List<Gedung>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Penyewaan Gedung") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(gedungList) { gedung ->
                GedungCard(
                    navController = navController,
                    gedung = gedung
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun GedungCard(
    navController: NavController,
    gedung: Gedung
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Nanti kita akan aktifkan ini untuk ke halaman detail
                 navController.navigate(Screen.Detail.createRoute(gedung.id))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = gedung.imageUrl,
                contentDescription = "Gambar ${gedung.nama ?: "Gedung"}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                // PENANGANAN NULL
                Text(text = gedung.nama ?: "Nama Gedung Tidak Tersedia", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                // PENANGANAN NULL
                Text(text = gedung.lokasi ?: "Lokasi tidak diketahui", color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // PENANGANAN NULL
                    val harga = gedung.hargaPerHari?.toInt() ?: 0
                    Text(text = "Rp $harga/hari", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)

                    Text(text = "⭐ ${gedung.penilaian ?: "N/A"}")
                }
            }
        }
    }
}

// ... (Composable LoadingScreen, ErrorScreen, dan BottomNavigationBar tidak perlu diubah) ...
@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = Color.Red, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Coba Lagi") }
    }
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Orders", Icons.Default.ShoppingCart),
        BottomNavItem("Profile", Icons.Default.AccountCircle)
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = item.title == "Home",
                onClick = { /* TODO: Handle navigation */ }
            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)