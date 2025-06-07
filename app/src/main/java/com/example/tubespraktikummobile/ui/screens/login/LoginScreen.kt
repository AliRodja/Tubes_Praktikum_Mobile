package com.example.tubespraktikummobile.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tubespraktikummobile.ui.navigation.Screen
import com.example.tubespraktikummobile.ui.theme.TubesPraktikumMobileTheme

// Definisikan warna agar mudah diubah
private val DarkBrown = Color(0xFF5D4037)
private val LightBeige = Color(0xFFF5EADD)
private val CardBeige = Color(0xFFFCEBBD)
private val TextColor = Color.Black


@Composable
fun LoginScreen(navController: NavController) {
    // State untuk menampung input dari user
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Latar belakang dua warna (tidak berubah)
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Bagian atas
                    .background(DarkBrown),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Reservasi Gedung Serba Guna",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f) // Bagian bawah
                    .background(LightBeige)
            )
        }

        // Card Login di tengah
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardBeige),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            // PERUBAHAN UTAMA: Tambahkan modifier verticalScroll ke Column ini
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .verticalScroll(rememberScrollState()), // <-- INI KUNCINYA!
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ... (Seluruh isi Column tidak ada yang berubah) ...
                Text(
                    text = "LOGIN",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Username", fontWeight = FontWeight.SemiBold, color = TextColor)
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("example@email.com") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DarkBrown,
                        unfocusedBorderColor = DarkBrown.copy(alpha = 0.5f),
                        cursorColor = DarkBrown
                    )
                )

                Text(text = "Password", fontWeight = FontWeight.SemiBold, color = TextColor)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("******") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DarkBrown,
                        unfocusedBorderColor = DarkBrown.copy(alpha = 0.5f),
                        cursorColor = DarkBrown
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* TODO: Logika login ditaruh di sini */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBrown)
                ) {
                    Text(text = "LOGIN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                SignUpText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun SignUpText(modifier: Modifier = Modifier, navController: NavController) {
    // ... (Tidak ada perubahan di sini) ...
    val annotatedString = buildAnnotatedString {
        append("Don't have account? ")
        pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
        withStyle(style = SpanStyle(color = DarkBrown, fontWeight = FontWeight.Bold)) {
            append("Sign Up")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                .firstOrNull()?.let {
                    navController.navigate(Screen.SignUp.route)
                }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    TubesPraktikumMobileTheme {
        LoginScreen(navController = rememberNavController())
    }
}