package com.example.uasppk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.uasppk.data.api.ApiClient
import com.example.uasppk.data.repository.RegisterRepository
import com.example.uasppk.ui.register.RegisterViewModelFactory
import com.example.uasppk.ui.register.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerRepository: RegisterRepository
    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(registerRepository)
    }
    private lateinit var signupButton: Button
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirmPasswordEditText: TextInputEditText
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize dependencies
        val apiService = ApiClient.getApiService(accessToken = null)
        registerRepository = RegisterRepository(apiService)

        // Initialize UI elements
        signupButton = findViewById(R.id.signupButton)
        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)
        loginLink = findViewById(R.id.loginLink)

        //setup signup button
        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            if (!validateInput(name, email, password, confirmPassword)) return@setOnClickListener

            // Register user
            registerViewModel.register(name, email, password)
        }

        // Observe register result
        registerViewModel.registerResult.observe(this) { result: String ->
            handleRegisterResult(result)
        }

        // Observe loading state
        registerViewModel.isLoading.observe(this) { isLoading: Boolean ->
            setLoadingState(isLoading)
        }

        // login link
        loginLink.setOnClickListener {
           navigateToAuth()
        }
    }

    //validate input
    private fun validateInput(name: String, email: String, password: String, confirmPassword: String): Boolean {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //handle register result
    private fun handleRegisterResult(result: String) {
        if (result.startsWith("Error")) {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_LONG).show()
            navigateToAuth()
        }
    }

    //set loading state
    private fun setLoadingState(isLoading: Boolean) {
        signupButton.isEnabled = !isLoading
        signupButton.text = if (isLoading) "Signing up..." else "Sign Up"
    }

    //navigate to auth
    private fun navigateToAuth(){
        val intent = Intent(this, Auth::class.java)
        startActivity(intent)
        finish()
    }
}