<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Validation\ValidationException;
use App\Http\Requests\Auth\RegisterRequest;
use App\Http\Requests\Auth\LoginRequest;
use App\Http\Resources\UserResource;
use App\Models\User;

class AuthController extends Controller
{
    public function register(RegisterRequest $request)
    {
        try {


            $user = User::create([
                'name' => $request->name,
                'email' => $request->email,
                'no_hp' => $request->no_hp,
                'password' => $request->password,

            ]);

            $token = $user->createToken('auth_token_' . $user->id)->plainTextToken;

            return response()->json([
                'message' => 'Registrasi berhasil!',
                'access_token' => $token,
                'token_type' => 'Bearer',
                'user' => new UserResource($user)
            ], 201);
        } catch (\Exception $e) {

            if ($e instanceof ValidationException) {
                return response()->json([
                    'message' => 'Validasi gagal',
                    'errors' => $e->errors()
                ], 422);
            }
            return response()->json([
                'message' => 'Terjadi kesalahan server.',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    public function login(LoginRequest $request)
    {
        try {
            $user = User::where('email', $request->email)->first();


            if (! $user || ! Hash::check($request->password, $user->password)) {
                throw ValidationException::withMessages([
                    'email' => ['Kredensial yang diberikan tidak cocok dengan catatan kami.'],
                ]);
            }


            $user->tokens()->delete();


            $token = $user->createToken('auth_token_' . $user->id)->plainTextToken;

            return response()->json([
                'message' => 'Login berhasil!',
                'access_token' => $token,
                'token_type' => 'Bearer',
                'user' => new UserResource($user)
            ]);
        } catch (ValidationException $e) {
            return response()->json([
                'message' => 'Login gagal',
                'errors' => $e->errors()
            ], 422);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Terjadi kesalahan server.',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    public function logout(Request $request)
    {
        try {
            $request->user()->currentAccessToken()->delete();

            return response()->json([
                'message' => 'Logout berhasil.'
            ]);
        } catch (\Exception $e) {
            return response()->json([
                'message' => 'Terjadi kesalahan server.',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    public function me(Request $request)
    {
        return new UserResource($request->user());
    }
}
