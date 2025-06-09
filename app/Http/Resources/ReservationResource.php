<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class ReservationResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'id' => $this->id,

            'pengguna' => new UserResource($this->whenLoaded('user')),
            'gedung' => new VenueResource($this->whenLoaded('venue')),

            'pembayaran' => new PaymentResource($this->whenLoaded('payment')),
            'waktu_mulai' => $this->waktu_mulai->format('Y-m-d H:i:s'),
            'waktu_selesai' => $this->waktu_selesai->format('Y-m-d H:i:s'),
            'durasi_hari' => $this->durasi_hari,
            'status' => $this->status,
            'dibuat_pada' => $this->created_at ? $this->created_at->format('Y-m-d H:i:s') : null,
            'diperbarui_pada' => $this->updated_at ? $this->updated_at->format('Y-m-d H:i:s') : null,
        ];
    }
}
