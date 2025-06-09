<?php

namespace Database\Seeders;

use App\Models\Payment;
use App\Models\Reservation;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class PaymentSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $reservationsWithoutPayment = Reservation::doesntHave('payment')->get();

        foreach ($reservationsWithoutPayment as $reservation) {
            Payment::factory()->create([
                'pemesanan_id' => $reservation->id,
            ]);
        }
    }
}
