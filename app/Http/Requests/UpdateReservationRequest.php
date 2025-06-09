<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Validation\Rule;

class UpdateReservationRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     */
    public function authorize(): bool
    {
        return Auth::check();
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, \Illuminate\Contracts\Validation\ValidationRule|array<mixed>|string>
     */
    public function rules(): array
    {
        return [

            "pengguna_id" => "sometimes|integer|exists:users,id",
            "gedung_id" => "sometimes|integer|exists:venues,id",
            "waktu_mulai" => "sometimes|date|after_or_equal:today",
            "waktu_selesai" => "sometimes|date|after_or_equal:waktu_mulai",
            "durasi_hari" => "sometimes|integer|min:1",

            "status"=> ["sometimes", "required", Rule::in(['menunggu', 'konfirmasi', 'dibatalkan'])],
        ];
    }

    public function messages(): array
    {
        return [
            'pengguna_id.exists' => 'Pengguna tidak ditemukan.',
            'gedung_id.exists' => 'Gedung tidak ditemukan.',
            'waktu_mulai.after_or_equal' => 'Waktu mulai tidak boleh di masa lalu.',
            'waktu_selesai.after_or_equal' => 'Waktu selesai harus setelah atau sama dengan waktu mulai.',
            'durasi_hari.min' => 'Durasi hari minimal 1.',
            'status.in' => 'Status pemesanan tidak valid.',
        ];
    }
}
