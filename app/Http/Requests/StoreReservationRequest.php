<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Validation\Rule;

class StoreReservationRequest extends FormRequest
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

            "pengguna_id" => "required|integer|exists:users,id",
            "gedung_id" => "required|integer|exists:venues,id",
            "waktu_mulai" => "required|date|after_or_equal:today",
            "waktu_selesai" => "required|date|after_or_equal:waktu_mulai",
            "durasi_hari" => "required|integer|min:1",

            "status"=> ["required", Rule::in(['menunggu', 'konfirmasi', 'dibatalkan'])],
        ];
    }

    protected function prepareForValidation()
    {
        if (!$this->has('pengguna_id') && Auth::check()) {
            $this->merge([
                'pengguna_id' => Auth::id(),
            ]);
        }
    }

    public function messages(): array
    {
        return [
            'pengguna_id.required' => 'ID Pengguna wajib diisi.',
            'pengguna_id.exists' => 'Pengguna tidak ditemukan.',
            'gedung_id.required' => 'ID Gedung wajib diisi.',
            'gedung_id.exists' => 'Gedung tidak ditemukan.',
            'waktu_mulai.required' => 'Waktu mulai wajib diisi.',
            'waktu_mulai.after_or_equal' => 'Waktu mulai tidak boleh di masa lalu.',
            'waktu_selesai.required' => 'Waktu selesai wajib diisi.',
            'waktu_selesai.after_or_equal' => 'Waktu selesai harus setelah atau sama dengan waktu mulai.',
            'durasi_hari.required' => 'Durasi hari wajib diisi.',
            'durasi_hari.min' => 'Durasi hari minimal 1.',
            'status.required' => 'Status pemesanan wajib diisi.',
            'status.in' => 'Status pemesanan tidak valid.',
        ];
    }
}
