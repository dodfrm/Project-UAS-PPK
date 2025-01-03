package com.example.uasppk.data.repository

import com.example.uasppk.data.api.ApiService
import com.example.uasppk.data.api.response.Contact
import retrofit2.Response

class ContactRepository(private val apiService: ApiService) {
    suspend fun getContacts(): Response<List<Contact>> {
        return apiService.getContacts()
    }
}