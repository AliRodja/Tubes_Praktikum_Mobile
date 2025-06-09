<?php

namespace App\Http\Controllers;

use App\Models\Payment;
use App\Http\Requests\StorePaymentRequest;
use App\Http\Requests\UpdatePaymentRequest;
use App\Http\Resources\PaymentResource;
use Illuminate\Http\Request;

class PaymentController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        try {

            $pembayarans = Payment::with('reservation')->get();
            return PaymentResource::collection($pembayarans);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal mengambil daftar pembayaran', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(StorePaymentRequest $request)
    {
        try {

            $pembayaran = Payment::create($request->validated());

            $pembayaran->load('reservation');
            return new PaymentResource($pembayaran);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal menyimpan pembayaran', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        try {
            $pembayaran = Payment::with('reservation')->find($id);

            if (!$pembayaran) {
                return response()->json(['message' => 'Pembayaran tidak ditemukan!'], 404);
            }

            return new PaymentResource($pembayaran);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal mengambil detail pembayaran', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(UpdatePaymentRequest $request, string $id)
    {
        try {
            $pembayaran = Payment::find($id);

            if (!$pembayaran) {
                return response()->json(['message' => 'Pembayaran tidak ditemukan!'], 404);
            }

            $pembayaran->update($request->validated());
            $pembayaran->load('reservation');
            return new PaymentResource($pembayaran);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal memperbarui pembayaran', 'error' => $e->getMessage()], 500);
        }
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        try {
            $pembayaran = Payment::find($id);

            if (!$pembayaran) {
                return response()->json(['message' => 'Pembayaran tidak ditemukan!'], 404);
            }

            $pembayaran->delete();
            return response()->json(['message' => 'Pembayaran berhasil dihapus!'], 200);
        } catch (\Exception $e) {
            return response()->json(['message' => 'Gagal menghapus pembayaran', 'error' => $e->getMessage()], 500);
        }
    }
}
