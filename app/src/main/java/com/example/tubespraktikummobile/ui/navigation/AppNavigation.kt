package com.example.tubespraktikummobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tubespraktikummobile.ui.screens.login.LoginScreen
import com.example.tubespraktikummobile.ui.screens.profile.ProfileScreen
import com.example.tubespraktikummobile.ui.screens.signup.SignUpScreen

// Definisikan rute untuk setiap layar agar tidak ada salah ketik
sealed class Screen(val route: String) {
    object Login: Screen("login_screen")
    object SignUp: Screen("signup_screen")
    object Profile : Screen("profile_screen")
}

@Composable
fun AppNavigation() {
    // Controller yang akan mengelola semua navigasi di aplikasi
    val navController = rememberNavController()

    // NavHost adalah container yang akan menampilkan layar sesuai rute
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route // Layar pertama yang muncul
    ) {
        // Mendefinisikan layar login
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        // Mendefinisikan layar sign up
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }

        // Mendefinisikan layar Profile
        composable(route = Screen.Profile.route) {
            SignUpScreen(navController = navController)
        }

        // TODO: Tambahkan layar lain di sini nanti
    }
}