<?php

namespace Database\Seeders;

use App\Models\Reservation;
use App\Models\User;
use App\Models\Venue;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class ReservationSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        if (User::count() == 0) {
            User::factory()->count(5)->create();
        }
        if (Venue::count() == 0) {
            Venue::factory()->count(3)->create();
        }

        Reservation::factory()->count(20)->create();
    }
}
