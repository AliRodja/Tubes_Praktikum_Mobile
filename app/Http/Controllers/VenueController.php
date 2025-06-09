<?php

namespace App\Http\Controllers;

use App\Http\Requests\StoreVenueRequest;
use App\Http\Requests\UpdateVenueRequest;
use App\Http\Resources\VenueResource;
use App\Models\Venue;
use Illuminate\Http\Request;

class VenueController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        try {
            return VenueResource::collection(Venue::all());
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal mengambil daftar gedung', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(StoreVenueRequest $request)
    {
        try {
            $gedung = Venue::create($request->validated());
            return new VenueResource($gedung);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal menyimpan gedung', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        try {
            $gedung = Venue::find($id);

            if (!$gedung) {
                return response()->json(['message' => 'Gedung tidak ditemukan!'], 404);
            }

            return new VenueResource($gedung);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal mengambil detail gedung', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdateVenueRequest $request, string $id)
    {
        try {
            $gedung = Venue::find($id);

            if (!$gedung) {
                return response()->json(['message' => 'Gedung tidak ditemukan!'], 404);
            }

            $gedung->update($request->validated());
            return new VenueResource($gedung);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal memperbarui gedung', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        try {
            $gedung = Venue::find($id);

            if (!$gedung) {
                return response()->json(['message' => 'Gedung tidak ditemukan!'], 404);
            }

            $gedung->delete();
            return response()->json([
                'message' => 'Gedung berhasil dihapus!'
            ], 200);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal menghapus gedung', 'error' => $e->getMessage()], 500);
        }
    }
}
