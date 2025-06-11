// di dalam data/local/SessionManager.kt
package com.example.tubespraktikummobile.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Membuat sebuah instance DataStore untuk seluruh aplikasi
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session_data")

class SessionManager(private val context: Context) {

    // Ini adalah "kunci" untuk menyimpan dan mengambil data token
    companion object {
        private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    // Fungsi untuk MENYIMPAN token ke DataStore
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
        }
    }

    // Fungsi untuk MENGAMBIL token dari DataStore.
    // Hasilnya adalah Flow, yaitu aliran data yang bisa kita pantau perubahannya.
    fun getAuthToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH_TOKEN]
        }
    }

    // Fungsi untuk MENGHAPUS token (untuk logout)
    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences.remove(KEY_AUTH_TOKEN)
        }
    }
}