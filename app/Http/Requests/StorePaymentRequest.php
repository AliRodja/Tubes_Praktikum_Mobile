<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Validation\Rule;

class StorePaymentRequest extends FormRequest
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

            'pemesanan_id' => ['required', 'integer', 'exists:reservations,id', 'unique:payments,pemesanan_id'],
            'jumlah' => ['required', 'numeric', 'min:0'],
            'status' => ['required', Rule::in(['berhasil', 'gagal'])],
            'dibayar_pada' => ['nullable', 'date'],
        ];
    }

    public function messages(): array
    {
        return [
            'pemesanan_id.required' => 'ID Pemesanan wajib diisi.',
            'pemesanan_id.exists' => 'Pemesanan tidak ditemukan.',
            'pemesanan_id.unique' => 'Pemesanan ini sudah memiliki pembayaran.',
            'jumlah.required' => 'Jumlah pembayaran wajib diisi.',
            'jumlah.numeric' => 'Jumlah pembayaran harus berupa angka.',
            'jumlah.min' => 'Jumlah pembayaran tidak boleh negatif.',
            'status.required' => 'Status pembayaran wajib diisi.',
            'status.in' => 'Status pembayaran tidak valid.',
        ];
    }
}
