<?php

namespace App\Http\Controllers;

use App\Models\Reservation;
use App\Http\Requests\StoreReservationRequest;
use App\Http\Requests\UpdateReservationRequest;
use App\Http\Resources\ReservationResource;
use Illuminate\Http\Request;

class ReservationController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        try {

            $pemesanans = Reservation::with(['user', 'venue', 'payment'])->get();
            return ReservationResource::collection($pemesanans);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal mengambil daftar pemesanan', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(StoreReservationRequest $request)
    {
        try {
            $pemesanan = Reservation::create($request->validated());

            $pemesanan->load(['user', 'venue', 'payment']);
            return new ReservationResource($pemesanan);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal menyimpan pemesanan', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Display the specified resource.
     */
    public function show(Reservation $reservation)
    {
        try {


            $reservation->load(['user', 'venue', 'payment']);
            return new ReservationResource($reservation);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal mengambil detail pemesanan', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdateReservationRequest $request, Reservation $reservation)
    {
        try {

            $reservation->update($request->validated());
            $reservation->load(['user', 'venue', 'payment']);
            return new ReservationResource($reservation);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal memperbarui pemesanan', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(Reservation $reservation)
    {
        try {

            $reservation->delete();
            return response()->json(['message' => 'Pemesanan berhasil dihapus!'], 200);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal menghapus pemesanan', 'error' => $e->getMessage()], 500);
        }
    }
}
