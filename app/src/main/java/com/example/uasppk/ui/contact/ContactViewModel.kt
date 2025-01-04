package com.example.uasppk.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasppk.data.api.response.Contact
import com.example.uasppk.data.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {

//    private val _contacts = MutableLiveData<List<Contact>>()
//    val contacts: LiveData<List<Contact>> = _contacts
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    fun fetchContacts() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val data = repository.getContacts()
//                _contacts.value = data
//            } catch (e: Exception) {
//                _contacts.value = emptyList() // Handle errors appropriately
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
}