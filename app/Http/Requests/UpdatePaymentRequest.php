<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Validation\Rule;

class UpdatePaymentRequest extends FormRequest
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



        $paymentId = $this->route('payment');

        return [


            'pemesanan_id' => [
                'sometimes',
                'integer',
                'exists:reservations,id',
                Rule::unique('payments', 'pemesanan_id')->ignore($paymentId, 'id')
            ],
            'jumlah' => ['sometimes', 'numeric', 'min:0'],
            'status' => ['sometimes', Rule::in(['berhasil', 'gagal'])],
            'dibayar_pada' => ['nullable', 'date'],
        ];
    }

    public function messages(): array
    {
        return [
            'pemesanan_id.exists' => 'Pemesanan tidak ditemukan.',
            'pemesanan_id.unique' => 'Pemesanan ini sudah memiliki pembayaran lain.',
            'jumlah.numeric' => 'Jumlah pembayaran harus berupa angka.',
            'jumlah.min' => 'Jumlah pembayaran tidak boleh negatif.',
            'status.in' => 'Status pembayaran tidak valid.',
        ];
    }
}
