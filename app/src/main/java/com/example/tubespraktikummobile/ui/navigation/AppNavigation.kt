// di ui/navigation/AppNavigation.kt
package com.example.tubespraktikummobile.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tubespraktikummobile.ui.main.MainViewModel
import com.example.tubespraktikummobile.ui.screens.home.HomeScreen
import com.example.tubespraktikummobile.ui.screens.login.LoginScreen
import com.example.tubespraktikummobile.ui.screens.signup.SignUpScreen
import com.example.tubespraktikummobile.ui.screens.detail.DetailScreen
import com.example.tubespraktikummobile.ui.screens.profile.ProfileScreen

sealed class Screen(val route: String) {
    object Login: Screen("login_screen")
    object SignUp: Screen("signup_screen")
    object Home: Screen("home_screen")
    object Detail: Screen("detail/{gedungId}") {
        fun createRoute(gedungId: Int) = "detail/$gedungId"
    }
    object Profile: Screen("profile_screen")
}

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel = viewModel()
) {
    // Ambil state dari MainViewModel
    val isLoading by mainViewModel.isLoading.collectAsState()
    val startDestination by mainViewModel.startDestination.collectAsState()

    val navController = rememberNavController()

    // Tampilkan loading indicator selagi ViewModel mengecek token
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Setelah selesai mengecek, tampilkan NavHost dengan layar awal yang benar
        NavHost(
            navController = navController,
            startDestination = startDestination // <-- Layar awal sekarang dinamis!
        ) {
            composable(route = Screen.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(route = Screen.SignUp.route) {
                SignUpScreen(navController = navController)
            }
            composable(route = Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("gedungId") { type = NavType.IntType })
            ) {
                DetailScreen(navController = navController)
            }
            composable(route = Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
}