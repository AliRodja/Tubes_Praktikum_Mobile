// di ui/screens/login/LoginScreen.kt
package com.example.tubespraktikummobile.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tubespraktikummobile.R
import com.example.tubespraktikummobile.ui.navigation.Screen
import com.example.tubespraktikummobile.ui.screens.auth.AuthEvent
import com.example.tubespraktikummobile.ui.screens.auth.AuthUiState
import com.example.tubespraktikummobile.ui.screens.auth.AuthViewModel

// Definisikan palet warna sesuai desain
val DarkBrown = Color(0xFF4A2C2A)
val LightBrown = Color(0xFFD7BCA2)
val BackgroundBeige = Color(0xFFF7F3E8)
val TextGray = Color(0xFF9E9E9E)

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val authState = authViewModel.authState
    val uiState = authViewModel.authUiState

    LaunchedEffect(key1 = true) {
        authViewModel.eventFlow.collect { event ->
            when (event) {
                is AuthEvent.LoginSuccess -> {
                    Toast.makeText(context, "Berhasil Masuk!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Home.route) {
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBeige) // Variabel warna digunakan di sini
    ) {
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
                    Text(
                        text = "MASUK",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    CustomLoginTextField(
                        label = "Email",
                        placeholder = "Masukan Email",
                        value = authState.email,
                        onValueChange = { authState.email = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomLoginTextField(
                        label = "Kata Sandi",
                        placeholder = "Masukkan kata sandi",
                        value = authState.password,
                        onValueChange = { authState.password = it },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { authViewModel.onLoginClicked() },
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
                            Text("LOGIN", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    SignUpText(
                        onSignUpClick = {
                            navController.navigate(Screen.SignUp.route)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomLoginTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        Text(text = label, color = DarkBrown, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(4.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(fontSize = 16.sp, color = DarkBrown),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            decorationBox = { innerTextField ->
                Column {
                    if (value.isEmpty()) {
                        Text(text = placeholder, color = TextGray)
                    }
                    innerTextField()
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(color = LightBrown, thickness = 1.dp)
                }
            }
        )
    }
}

@Composable
fun SignUpText(onSignUpClick: () -> Unit, modifier: Modifier = Modifier) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = DarkBrown)) {
            append("Tidak memiliki akun? ")
        }
        pushStringAnnotation(tag = "SIGNUP", annotation = "signup")
        withStyle(style = SpanStyle(color = DarkBrown, fontWeight = FontWeight.Bold)) {
            append("Daftar Sekarang")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "SIGNUP", start = offset, end = offset)
                .firstOrNull()?.let {
                    onSignUpClick()
                }
        },
        modifier = modifier
    )
}