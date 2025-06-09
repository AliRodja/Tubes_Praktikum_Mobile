<?php

namespace Database\Seeders;

use App\Models\User;
// use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     */
    public function run(): void
    {
        User::factory()->create([
            'name' => 'Test User',
            'email' => 'test@example.com',
            "no_hp" => "081234567890",
        ]);

        $this->call([
            VenueSeeder::class,
            ReservationSeeder::class,
            PaymentSeeder::class,
        ]);
    }
}
