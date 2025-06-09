<?php

namespace Database\Factories;

use App\Models\Payment;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Payment>
 */
class PaymentFactory extends Factory
{
    protected $model = Payment::class;

    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'pemesanan_id' => null,
            'jumlah' => fake()->randomFloat(2, 100000, 5000000),
            'status' => fake()->randomElement(['berhasil', 'gagal']),
            'dibayar_pada' => fake()->boolean(80) ? fake()->dateTimeBetween('-1 month', 'now') : null,
        ];
    }
}
