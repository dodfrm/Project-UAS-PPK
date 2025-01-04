package com.example.uasppk.data.repository

import com.example.uasppk.data.api.ApiService
import com.example.uasppk.data.api.request.RegisterRequest
import com.example.uasppk.data.api.response.RegisterResponse

class RegisterRepository(private val apiService: ApiService)  {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val response = apiService.register(RegisterRequest(name, email, password))
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty response body")
        } else {
            val errorMessage = when (response.code()) {
                400 -> "Bad Request: ${response.errorBody()?.string()}"
                404 -> "Page Not Found"
                500 -> "Server Error: Please try again later"
                else -> "Unexpected Error: ${response.code()}"
            }
            throw Exception(errorMessage)
        }
    }
}