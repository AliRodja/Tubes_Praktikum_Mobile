<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('reservations', function (Blueprint $table) {
            $table->id();
            $table->foreignId('pengguna_id')->constrained('users')->onDelete('cascade'); // sesuaikan dengan nama tabel user
            $table->foreignId('gedung_id')->constrained('venues')->onDelete('cascade'); // sesuaikan dengan nama tabel venue
            $table->timestamp('waktu_mulai');
            $table->timestamp('waktu_selesai');
            $table->integer('durasi_hari');
            $table->enum('status', ['menunggu', 'konfirmasi', 'dibatalkan'])->default('menunggu');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('reservations');
    }
};
