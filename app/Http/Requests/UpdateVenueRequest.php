<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class UpdateVenueRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     */
    public function authorize(): bool
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, \Illuminate\Contracts\Validation\ValidationRule|array<mixed>|string>
     */
    public function rules(): array
    {
        return [
            'name' => 'sometimes|required|string|max:100',
            'lokasi' => 'sometimes|required|string|max:100',
            'kapasitas' => 'sometimes|required|integer|min:1',
            'harga_per_hari' => 'sometimes|required|numeric|min:0',
            'penilaian' => 'nullable|numeric|min:0|max:5',
            'fasilitas' => 'nullable|string',
        ];
    }

    public function messages(): array
    {
return [
            'name.required' => 'Nama gedung wajib diisi.',
            'lokasi.required' => 'Lokasi gedung wajib diisi.',
            'kapasitas.required' => 'Kapasitas gedung wajib diisi.',
            'kapasitas.integer' => 'Kapasitas harus berupa angka.',
            'kapasitas.min' => 'Kapasitas minimal 1.',
            'harga_per_hari.required' => 'Harga per hari wajib diisi.',
            'harga_per_hari.numeric' => 'Harga per hari harus berupa angka.',
            'harga_per_hari.min' => 'Harga per hari tidak boleh negatif.',
        ];
    }
}
