package com.example.uasppk.data.api

import com.example.uasppk.data.api.request.AuthRequest
import com.example.uasppk.data.api.response.Contact
import com.example.uasppk.data.api.response.JwtResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body authRequest: AuthRequest): Response<JwtResponse>

    //endpoint Contacts
    @GET("api/contacts")
    suspend fun getContacts(): Response<List<Contact>>

}