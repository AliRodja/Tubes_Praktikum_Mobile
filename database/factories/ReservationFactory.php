<?php

namespace Database\Factories;

use App\Models\Reservation;
use App\Models\User;
use App\Models\Venue;
use Illuminate\Database\Eloquent\Factories\Factory;
use Carbon\Carbon;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Reservation>
 */
class ReservationFactory extends Factory
{
    protected $model = Reservation::class;

    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        $waktuMulai = Carbon::parse(fake()->dateTimeBetween('now', '+6 months'));
        $durasiHari = fake()->numberBetween(1, 7);
        $waktuSelesai = (clone $waktuMulai)->addDays($durasiHari);

        return [
            'pengguna_id' => User::factory(),
            'gedung_id' => Venue::factory(),
            'waktu_mulai' => $waktuMulai,
            'waktu_selesai' => $waktuSelesai,
            'durasi_hari' => $durasiHari,
            'status' => fake()->randomElement(['menunggu', 'konfirmasi', 'dibatalkan']), // Sesuai enum di migrasi
        ];
    }
}
