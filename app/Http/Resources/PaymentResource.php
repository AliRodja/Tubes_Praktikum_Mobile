<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class PaymentResource extends JsonResource
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
            'pemesanan' => new ReservationResource($this->whenLoaded('reservation')),
            'jumlah' => (float) $this->jumlah,
            'status' => $this->status,
            'dibayar_pada' => $this->dibayar_pada ? $this->dibayar_pada->format('Y-m-d H:i:s') : null,
        ];
    }
}
