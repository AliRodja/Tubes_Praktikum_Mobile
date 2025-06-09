<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasOne;

class Reservation extends Model
{
    use HasFactory;

    protected $guarded = ['id'];

    protected $casts = [
        'waktu_mulai'=> 'datetime',
        'waktu_selesai'=> 'datetime',
    ];

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class, 'pengguna_id', 'id');
    }


    public function venue(): BelongsTo
    {
        return $this->belongsTo(Venue::class,'gedung_id','id');
    }


    public function payment(): HasOne
    {
        return $this->hasOne(Payment::class, 'pemesanan_id','id');
    }
}
