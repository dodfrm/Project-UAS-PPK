package com.example.uasppk.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.uasppk.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val jwtResponse = repository.login(email, password)
                _loginResult.value = jwtResponse.accessToken
            } catch (e: Exception) {
                _loginResult.value = "Error: ${e.message}"
            }
        }
    }
}