package com.example.uasppk.data.repository

import com.example.uasppk.data.api.request.AuthRequest
import com.example.uasppk.data.api.response.JwtResponse
import com.example.uasppk.data.api.ApiService

class AuthRepository(private val apiService: ApiService){
    suspend fun login(email: String, password: String): JwtResponse {
        val response = apiService.login(AuthRequest(email, password))
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty response body")
        } else {
            val errorMessage = when (response.code()) {
                400 -> "Bad Request: ${response.errorBody()?.string()}"
                404 -> "Page Not Found"
                401 -> "Unauthorized: Invalid email or password"
                500 -> "Server Error: Please try again later"
                else -> "Unexpected Error: ${response.code()}"
            }
            throw Exception(errorMessage)
        }
    }
}