<?php

namespace Database\Factories;

use App\Models\Venue;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\Venue>
 */
class VenueFactory extends Factory
{
    protected $model = Venue::class;

    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'name' => fake()->word() . ' Hall',
            'lokasi' => fake()->city() . ', ' . fake()->state(),
            'kapasitas' => fake()->numberBetween(50, 1000),
            'harga_per_hari' => fake()->randomFloat(2, 500000, 10000000),
            'penilaian' => fake()->randomFloat(1, 1, 5),
            'fasilitas' => fake()->sentence(5),
        ];
    }
}
