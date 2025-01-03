package com.example.uasppk.data.api.response

data class Contact(
    val fullName: String,
    val phone: String,
    val email: String,
    val contactType: String,
    val contactOrganizations: List<String>,
    val contactSubjects: List<String>
)
