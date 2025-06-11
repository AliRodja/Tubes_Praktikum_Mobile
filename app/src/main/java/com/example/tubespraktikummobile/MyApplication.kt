// di dalam com/example/tubespraktikummobile/MyApplication.kt
package com.example.tubespraktikummobile

import android.app.Application
import com.example.tubespraktikummobile.data.local.SessionManager

class MyApplication : Application() {
    // Kita gunakan 'lazy' agar SessionManager hanya dibuat saat pertama kali dibutuhkan
    val sessionManager: SessionManager by lazy {
        SessionManager(this)
    }

    // Ini akan membuat satu instance untuk seluruh aplikasi
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}