// di data/network/ApiService.kt
package com.example.tubespraktikummobile.data.network

import com.example.tubespraktikummobile.data.GedungApiResponse
import com.example.tubespraktikummobile.data.GedungDetailApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("gedung")
    suspend fun getAllGedung(): GedungApiResponse

    @GET("gedung/{id}")
    suspend fun getGedungById(@Path("id") id: Int): GedungDetailApiResponse
    // --- TAMBAHKAN DUA FUNGSI DI BAWAH INI ---

    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): LoginResponse
}