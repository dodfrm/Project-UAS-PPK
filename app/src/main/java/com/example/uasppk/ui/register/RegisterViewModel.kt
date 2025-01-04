package com.example.uasppk.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uasppk.data.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository): ViewModel() {
    private val _register = MutableLiveData<String>()
    val registerResult: LiveData<String> = _register

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val data = repository.register(name, email, password)
                _register.value = data.toString()
            } catch (e: Exception) {
                _register.value = "Error: ${e.message}"
            }
        }
    }
}