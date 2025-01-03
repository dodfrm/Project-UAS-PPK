package com.example.uasppk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.uasppk.data.api.ApiClient
import com.example.uasppk.data.repository.AuthRepository
import com.example.uasppk.ui.auth.AuthViewModel
import com.example.uasppk.ui.auth.AuthViewModelFactory
import com.example.uasppk.utils.SharedPreferencesHelper
import com.google.android.material.textfield.TextInputEditText

class Auth : AppCompatActivity() {
    private lateinit var authRepository: AuthRepository
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var loginButton: Button
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signupLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Initialize dependencies
        val apiService = ApiClient.getApiService(accessToken = null)
        authRepository = AuthRepository(apiService)
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        // Initialize UI elements
        loginButton = findViewById(R.id.loginButton)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        signupLink = findViewById(R.id.signupLink)

        // Check previous login status
        if (sharedPreferencesHelper.isLoggedIn()) {
            navigateToHome()
            return
        }

        // Set up login button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (!validateInput(email, password)) return@setOnClickListener

            // Perform login
            authViewModel.login(email, password)
        }

        // Observe login result
        authViewModel.loginResult.observe(this) { result: String ->
            handleLoginResult(result)
        }

        // Observe loading state
        authViewModel.isLoading.observe(this) { isLoading: Boolean ->
            setLoadingState(isLoading)
        }

        // Set up sign-up link (if needed)
        signupLink.setOnClickListener {
            Toast.makeText(this, "Navigasi ke halaman daftar belum diimplementasi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun handleLoginResult(result: String) {
        if (result.startsWith("Error")) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        } else {
            sharedPreferencesHelper.saveJwtToken(result)
            Toast.makeText(this, "Login berhasil", Toast.LENGTH_LONG).show()
            navigateToHome()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        loginButton.isEnabled = !isLoading
        loginButton.text = if (isLoading) "Logging in..." else "Login"
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
