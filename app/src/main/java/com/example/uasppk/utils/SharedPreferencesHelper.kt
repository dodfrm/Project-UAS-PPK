package com.example.uasppk.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context:Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_JWT_TOKEN = "accessToken"
    }

    // Simpan token JWT
    fun saveJwtToken(accessToken: String) {
        sharedPreferences.edit().putString(KEY_JWT_TOKEN, accessToken).apply()
    }

    // Ambil token JWT
    fun getJwtToken(): String? {
        return sharedPreferences.getString(KEY_JWT_TOKEN, null)
    }

    // Hapus token JWT
    fun clearJwtToken() {
        sharedPreferences.edit().remove(KEY_JWT_TOKEN).apply()
    }

    fun isLoggedIn(): Boolean {
        return getJwtToken() != null
    }
}