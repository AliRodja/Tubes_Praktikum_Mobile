package com.example.tubespraktikummobile.ui.screens.signup

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
fun SignUpScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(DarkBrown)
                    .padding(top = 75.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Reservasi Gedung\nSerba Guna",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(LightBeige))
        }

        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 160.dp)
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "SIGN UP", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextColor, modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(12.dp))

                // Input Fields
                Text(text = "Username", fontWeight = FontWeight.SemiBold, color = TextColor)
                CustomTextField(value = username, onValueChange = { username = it }, placeholder = "contoh@gmail.com")

                Text(text = "Phone Number", fontWeight = FontWeight.SemiBold, color = TextColor)
                CustomTextField(value = phone, onValueChange = { phone = it }, placeholder = "+62 8123456789", keyboardType = KeyboardType.Phone)

                Text(text = "Password", fontWeight = FontWeight.SemiBold, color = TextColor)
                CustomTextField(value = password, onValueChange = { password = it }, placeholder = "********", isPassword = true)

                Text(text = "Confirm Password", fontWeight = FontWeight.SemiBold, color = TextColor)
                CustomTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, placeholder = "********", isPassword = true)

                Spacer(modifier = Modifier.height(20.dp))

                // Tombol Sign Up
                Button(
                    onClick = { /* TODO: Logika Sign Up */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBrown)
                ) {
                    Text(text = "SIGN UP", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                LoginText(modifier = Modifier.align(Alignment.CenterHorizontally), navController = navController)
            }
        }
    }
}

// TextField yang dibuat custom agar tidak berulang
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DarkBrown,
            unfocusedBorderColor = DarkBrown.copy(alpha = 0.5f),
            cursorColor = DarkBrown
        )
    )
}

// Teks untuk kembali ke Login
@Composable
fun LoginText(modifier: Modifier = Modifier, navController: NavController) {
    val annotatedString = buildAnnotatedString {
        append("Already have an account? ")
        pushStringAnnotation(tag = "Login", annotation = "Login")
        withStyle(style = SpanStyle(color = DarkBrown, fontWeight = FontWeight.Bold)) {
            append("Login")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "Login", start = offset, end = offset)
                .firstOrNull()?.let {
                    // Kembali ke layar login, membersihkan backstack
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
        },
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    TubesPraktikumMobileTheme {
        SignUpScreen(rememberNavController())
    }
}