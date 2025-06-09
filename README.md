# Dokumentasi REST API Reservasi Gedung

Dokumen ini menyajikan spesifikasi REST API untuk sistem reservasi gedung, yang dikembangkan menggunakan kerangka kerja Laravel dengan implementasi otentikasi melalui Laravel Sanctum.

## 🌐 Base URL

Seluruh permintaan API harus diarahkan ke *base URL* berikut: `http://localhost:8000/api` atau `http://Tubes_Praktikum_Mobile.test/api`.

## 🔐 Otentikasi (Laravel Sanctum)

Beberapa *endpoint* dalam API ini memerlukan otentikasi. Setelah berhasil melakukan proses *login*, sebuah `access_token` akan diberikan. Token ini wajib disertakan dalam *header* setiap permintaan yang dilindungi, mengikuti format `Bearer Token`.

**Struktur Header Otentikasi:**
`Authorization: Bearer <token_valid>`

`<token_valid>` harus diganti dengan nilai *access_token* yang diperoleh dari *endpoint login* yang berhasil.

## 🛑 Respons Kesalahan Umum

API ini dirancang untuk mengembalikan respons JSON standar ketika terjadi kondisi kesalahan.

* **Validasi Data Tidak Valid (`HTTP 422 Unprocessable Content`):**
    Terjadi ketika data yang diajukan tidak memenuhi kriteria validasi yang ditetapkan.
    ```json
    {
        "message": "The given data was invalid.",
        "errors": {
            "email": [
                "Alamat email ini sudah terdaftar."
            ]
        }
    }
    ```

* **Tidak Terotentikasi (`HTTP 401 Unauthorized`):**
    Indikasi bahwa permintaan memerlukan otentikasi, namun token yang diberikan tidak valid, kedaluwarsa, atau tidak ada.
    ```json
    {
        "message": "Unauthenticated."
    }
    ```

* **Sumber Daya Tidak Ditemukan (`HTTP 404 Not Found`):**
    Menunjukkan bahwa sumber daya (data) yang diminta tidak dapat ditemukan di server.
    ```json
    {
        "message": "Sumber daya tidak ditemukan."
    }
    ```

* **Kesalahan Internal Server (`HTTP 500 Internal Server Error`):**
    Menunjukkan terjadinya kesalahan tak terduga pada sisi server.
    ```json
    {
        "message": "Terjadi kesalahan pada server.",
        "error": "Detail pesan kesalahan dari server"
    }
    ```

## 👤 Endpoint Otentikasi (Tag: `Auth`)

Bagian ini mencakup *endpoint* yang berkaitan dengan manajemen otentikasi pengguna.

### **1. Pendaftaran Pengguna Baru**
* **Deskripsi:** Memfasilitasi pendaftaran pengguna baru ke dalam sistem.
* **URL:** `/register`
* **Metode:** `POST`
* **Badan Permintaan (JSON):**
    ```json
    {
        "name": "Nama Lengkap Pengguna",
        "email": "email_unik@contoh.com",
        "no_hp": "08123456789",
        "password": "KataSandiSangatAman123#",
        "password_confirmation": "KataSandiSangatAman123#"
    }
    ```
* **Respons (HTTP 201 Created):**
    ```json
    {
        "message": "Pendaftaran berhasil diselesaikan.",
        "access_token": "2|hJ2f...<token_sanctum_panjang>",
        "token_type": "Bearer",
        "user": {
            "id": 1,
            "name": "Nama Lengkap Pengguna",
            "email": "email_unik@contoh.com",
            "no_hp": "08123456789",
            "dibuat_pada": "2025-06-09 14:00:00"
        }
    }
    ```

### **2. Otentikasi Pengguna (Login)**
* **Deskripsi:** Memungkinkan pengguna untuk masuk ke akun dan memperoleh *Bearer Token* untuk akses ke *endpoint* yang dilindungi.
* **URL:** `/login`
* **Metode:** `POST`
* **Badan Permintaan (JSON):**
    ```json
    {
        "email": "email_terdaftar@contoh.com",
        "password": "KataSandiSangatAman123#"
    }
    ```
* **Respons (HTTP 200 OK):**
    ```json
    {
        "message": "Proses masuk berhasil.",
        "access_token": "2|hJ2f...<token_sanctum_baru>",
        "token_type": "Bearer",
        "user": {
            "id": 1,
            "name": "Nama Lengkap Pengguna",
            "email": "email_terdaftar@contoh.com",
            "no_hp": "08123456789",
            "dibuat_pada": "2025-06-09 14:00:00"
        }
    }
    ```

### **3. Pengakhiran Sesi Pengguna (Logout)**
* **Deskripsi:** Mengakhiri sesi pengguna dan mencabut *Bearer Token* yang sedang aktif.
* **URL:** `/logout`
* **Metode:** `POST`
* **Keamanan:** Bearer Token
* **Respons (HTTP 200 OK):**
    ```json
    {
        "message": "Proses keluar berhasil."
    }
    ```

### **4. Pengambilan Informasi Pengguna Terotentikasi**
* **Deskripsi:** Mengambil detail informasi dari pengguna yang saat ini terotentikasi.
* **URL:** `/user`
* **Metode:** `GET`
* **Keamanan:** Bearer Token
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": {
            "id": 1,
            "name": "Nama Lengkap Pengguna",
            "email": "email_terdaftar@contoh.com",
            "no_hp": "08123456789",
            "dibuat_pada": "2025-06-09 14:00:00"
        }
    }
    ```

## 🏟️ Endpoint Manajemen Venue (Tag: `Venues`)

Bagian ini menjelaskan *endpoint* yang terkait dengan pengelolaan data gedung atau *venue*.

### **1. Daftar Semua Venue**
* **Deskripsi:** Mengambil daftar komprehensif dari semua *venue* yang tersedia dalam sistem.
* **URL:** `/venues`
* **Metode:** `GET`
* **Keamanan:** Bearer Token
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": [
            {
                "id": 1,
                "nama": "Aula Konvensi Megah",
                "lokasi": "Jl. Raya Bandung No. 123",
                "kapasitas": 500,
                "harga_per_hari": 8500000.00,
                "penilaian": 4.8,
                "fasilitas": "AC, Sistem Suara Profesional",
                "created_at": "2025-06-09 14:05:00",
                "updated_at": "2025-06-09 14:05:00"
            }
        ]
    }
    ```

### **2. Pembuatan Venue Baru**
* **Deskripsi:** Menambahkan entri *venue* baru ke dalam basis data sistem.
* **URL:** `/venues`
* **Metode:** `POST`
* **Keamanan:** Bearer Token
* **Badan Permintaan (JSON):**
    ```json
    {
        "name": "Gedung Pernikahan Harmoni",
        "lokasi": "Kota Yogyakarta, DIY",
        "kapasitas": 300,
        "harga_per_hari": 7000000.00,
        "penilaian": 4.7,
        "fasilitas": "Area luar ruangan, Ruang rias, Paket katering"
    }
    ```
* **Respons (HTTP 201 Created):**
    ```json
    {
        "data": {
            "id": 3,
            "nama": "Gedung Pernikahan Harmoni",
            "lokasi": "Kota Yogyakarta, DIY",
            "kapasitas": 300,
            "harga_per_hari": 7000000.00,
            "penilaian": 4.7,
            "fasilitas": "Area luar ruangan, Ruang rias, Paket katering",
            "created_at": "2025-06-09 14:10:00",
            "updated_at": "2025-06-09 14:10:00"
        }
    }
    ```

### **3. Pengambilan Detail Satu Venue**
* **Deskripsi:** Mengambil detail spesifik dari sebuah *venue* berdasarkan identifikasi uniknya (ID).
* **URL:** `/venues/{id}`
* **Metode:** `GET`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari *venue*.
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": {
            "id": 1,
            "nama": "Aula Konvensi Megah",
            "lokasi": "Jl. Raya Bandung No. 123",
            "kapasitas": 500,
            "harga_per_hari": 8500000.00,
            "penilaian": 4.8,
            "fasilitas": "AC, Sistem Suara Profesional",
            "created_at": "2025-06-09 14:05:00",
            "updated_at": "2025-06-09 14:05:00"
        }
    }
    ```

### **4. Pembaruan Data Venue**
* **Deskripsi:** Melakukan pembaruan pada data *venue* yang sudah ada. Metode `PATCH` digunakan untuk pembaruan parsial.
* **URL:** `/venues/{id}`
* **Metode:** `PATCH`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari *venue*.
* **Badan Permintaan (JSON):**
    ```json
    {
        "name": "Gedung Pernikahan Harmoni Baru",
        "kapasitas": 350
    }
    ```
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": {
            "id": 3,
            "nama": "Gedung Pernikahan Harmoni Baru",
            "lokasi": "Kota Yogyakarta, DIY",
            "kapasitas": 350,
            "harga_per_hari": 7000000.00,
            "penilaian": 4.7,
            "fasilitas": "Area luar ruangan, Ruang rias, Paket katering",
            "created_at": "2025-06-09 14:10:00",
            "updated_at": "2025-06-09 14:15:00"
        }
    }
    ```

### **5. Penghapusan Venue**
* **Deskripsi:** Menghapus sebuah *venue* dari sistem.
* **URL:** `/venues/{id}`
* **Metode:** `DELETE`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari *venue*.
* **Respons (HTTP 200 OK):**
    ```json
    {
        "message": "Venue berhasil dihapus."
    }
    ```

## 📆 Endpoint Manajemen Reservasi (Tag: `Reservations`)

Bagian ini menguraikan *endpoint* yang berkaitan dengan pengelolaan data reservasi gedung.

### **1. Daftar Semua Reservasi**
* **Deskripsi:** Mengambil daftar lengkap dari semua reservasi yang tercatat dalam sistem.
* **URL:** `/reservations`
* **Metode:** `GET`
* **Keamanan:** Bearer Token
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": [
            {
                "id": 1,
                "pengguna": {
                    "id": 1, "name": "Aldi Pratama", "email": "aldi.pratama@example.com", "no_hp": "08123456789", "dibuat_pada": "2025-06-09 14:00:00"
                },
                "gedung": {
                    "id": 1, "nama": "Aula Konvensi Megah", "lokasi": "Bandung", "kapasitas": 500, "harga_per_hari": 8500000.00, "penilaian": 4.8, "fasilitas": "AC, Sistem Suara"
                },
                "pembayaran": null,
                "waktu_mulai": "2025-07-15 09:00:00",
                "waktu_selesai": "2025-07-17 18:00:00",
                "durasi_hari": 3,
                "status": "menunggu",
                "dibuat_pada": "2025-06-09 14:20:00",
                "diperbarui_pada": "2025-06-09 14:20:00"
            }
        ]
    }
    ```

### **2. Pembuatan Reservasi Baru**
* **Deskripsi:** Menciptakan entri reservasi baru untuk pengguna dan *venue* yang spesifik.
* **URL:** `/reservations`
* **Metode:** `POST`
* **Keamanan:** Bearer Token
* **Badan Permintaan (JSON):**
    ```json
    {
        "pengguna_id": 1,
        "gedung_id": 1,
        "waktu_mulai": "2025-08-01 10:00:00",
        "waktu_selesai": "2025-08-03 17:00:00",
        "durasi_hari": 3,
        "status": "menunggu"
    }
    ```
* **Respons (HTTP 201 Created):**
    ```json
    {
        "data": {
            "id": 2,
            "pengguna": { /* ... data pengguna ... */ },
            "gedung": { /* ... data venue ... */ },
            "pembayaran": null,
            "waktu_mulai": "2025-08-01 10:00:00",
            "waktu_selesai": "2025-08-03 17:00:00",
            "durasi_hari": 3,
            "status": "menunggu",
            "dibuat_pada": "2025-06-09 14:25:00",
            "diperbarui_pada": "2025-06-09 14:25:00"
        }
    }
    ```

### **3. Pengambilan Detail Satu Reservasi**
* **Deskripsi:** Mengambil detail spesifik dari sebuah reservasi berdasarkan identifikasi uniknya (ID).
* **URL:** `/reservations/{id}`
* **Metode:** `GET`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari reservasi.
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": {
            "id": 1,
            "pengguna": { /* ... data pengguna ... */ },
            "gedung": { /* ... data venue ... */ },
            "pembayaran": null,
            "waktu_mulai": "2025-07-15 09:00:00",
            "waktu_selesai": "2025-07-17 18:00:00",
            "durasi_hari": 3,
            "status": "menunggu",
            "dibuat_pada": "2025-06-09 14:20:00",
            "diperbarui_pada": "2025-06-09 14:20:00"
        }
    }
    ```

### **4. Pembaruan Data Reservasi**
* **Deskripsi:** Melakukan pembaruan pada data reservasi yang sudah ada. Metode `PATCH` digunakan untuk pembaruan parsial.
* **URL:** `/reservations/{id}`
* **Metode:** `PATCH`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari reservasi.
* **Badan Permintaan (JSON):**
    ```json
    {
        "status": "konfirmasi"
    }
    ```
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": {
            "id": 2,
            "pengguna": { /* ... data pengguna ... */ },
            "gedung": { /* ... data venue ... */ },
            "pembayaran": null,
            "waktu_mulai": "2025-08-01 10:00:00",
            "waktu_selesai": "2025-08-03 17:00:00",
            "durasi_hari": 3,
            "status": "konfirmasi",
            "dibuat_pada": "2025-06-09 14:25:00",
            "diperbarui_pada": "2025-06-09 14:30:00"
        }
    }
    ```

### **5. Penghapusan Reservasi**
* **Deskripsi:** Menghapus sebuah reservasi dari sistem.
* **URL:** `/reservations/{id}`
* **Metode:** `DELETE`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari reservasi.
* **Respons (HTTP 200 OK):**
    ```json
    {
        "message": "Reservasi berhasil dihapus."
    }
    ```

## 💳 Endpoint Manajemen Pembayaran (Tag: `Payments`)

Bagian ini merinci *endpoint* yang bertanggung jawab atas pengelolaan data pembayaran reservasi.

### **1. Daftar Semua Pembayaran**
* **Deskripsi:** Mengambil daftar lengkap dari semua pembayaran yang tercatat dalam sistem.
* **URL:** `/payments`
* **Metode:** `GET`
* **Keamanan:** Bearer Token
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": [
            {
                "id": 1,
                "pemesanan": {
                    "id": 1, "waktu_mulai": "2025-07-15 09:00:00", "status": "menunggu"
                },
                "jumlah": 8500000.00,
                "status": "berhasil",
                "dibayar_pada": "2025-06-09 14:35:00"
            }
        ]
    }
    ```

### **2. Pembuatan Pembayaran Baru**
* **Deskripsi:** Menciptakan entri pembayaran baru untuk reservasi yang spesifik.
* **URL:** `/payments`
* **Metode:** `POST`
* **Keamanan:** Bearer Token
* **Badan Permintaan (JSON):**
    ```json
    {
        "pemesanan_id": 2,
        "jumlah": 6500000.00,
        "status": "berhasil",
        "dibayar_pada": "2025-08-01 12:00:00"
    }
    ```
* **Respons (HTTP 201 Created):**
    ```json
    {
        "data": {
            "id": 2,
            "pemesanan": { /* ... data reservasi ... */ },
            "jumlah": 6500000.00,
            "status": "berhasil",
            "dibayar_pada": "2025-08-01 12:00:00"
        }
    }
    ```

### **3. Pengambilan Detail Satu Pembayaran**
* **Deskripsi:** Mengambil detail spesifik dari sebuah pembayaran berdasarkan identifikasi uniknya (ID).
* **URL:** `/payments/{id}`
* **Metode:** `GET`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari pembayaran.
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": {
            "id": 1,
            "pemesanan": { /* ... data reservasi ... */ },
            "jumlah": 8500000.00,
            "status": "berhasil",
            "dibayar_pada": "2025-06-09 14:35:00"
        }
    }
    ```

### **4. Pembaruan Data Pembayaran**
* **Deskripsi:** Melakukan pembaruan pada data pembayaran yang sudah ada. Metode `PATCH` digunakan untuk pembaruan parsial.
* **URL:** `/payments/{id}`
* **Metode:** `PATCH`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari pembayaran.
* **Badan Permintaan (JSON):**
    ```json
    {
        "status": "gagal"
    }
    ```
* **Respons (HTTP 200 OK):**
    ```json
    {
        "data": {
            "id": 2,
            "pemesanan": { /* ... data reservasi ... */ },
            "jumlah": 6500000.00,
            "status": "gagal",
            "dibayar_pada": "2025-08-01 12:00:00"
        }
    }
    ```

### **5. Penghapusan Pembayaran**
* **Deskripsi:** Menghapus sebuah pembayaran dari sistem.
* **URL:** `/payments/{id}`
* **Metode:** `DELETE`
* **Keamanan:** Bearer Token
* **Parameter Jalur (`Path Parameters`):**
    * `id` (integer, wajib): Identifikasi unik (*ID*) dari pembayaran.
* **Respons (HTTP 200 OK):**
    ```json
    {
        "message": "Pembayaran berhasil dihapus."
    }
    ```
