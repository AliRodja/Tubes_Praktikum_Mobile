package com.example.tubespraktikummobile.ui.screens.home

// di dalam file ui/screens/home/HomeScreen.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel() // Ambil instance ViewModel
) {
    // Ambil data dari ViewModel dan "dengarkan" perubahannya
    val textState by homeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tampilkan data dari state
        Text(text = textState)

        // Tombol ini memanggil fungsi di ViewModel
        Button(onClick = { homeViewModel.onButtonClicked() }) {
            Text("Klik Aku")
        }
    }
}