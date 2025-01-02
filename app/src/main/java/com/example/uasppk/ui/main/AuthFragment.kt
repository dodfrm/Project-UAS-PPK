package com.example.uasppk.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.uasppk.R
import com.example.uasppk.data.api.ApiClient
import com.example.uasppk.data.repository.AuthRepository
import com.example.uasppk.ui.auth.AuthViewModel
import com.example.uasppk.ui.auth.AuthViewModelFactory
import com.example.uasppk.utils.SharedPreferencesHelper

class AuthFragment : Fragment() {
    private lateinit var authRepository: AuthRepository
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = ApiClient.getApiService(token = null)
        authRepository = AuthRepository(apiService)
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        loginButton = view.findViewById(R.id.loginButton)
        emailEditText = view.findViewById(R.id.email)
        passwordEditText = view.findViewById(R.id.password)

        // Check previous login status
        if (sharedPreferencesHelper.isLoggedIn()) {
            navigateToHome()
            return
        }

        // Set up login button
        loginButton.setOnClickListener {
            handleLogin()
        }

        // Observe login result
        authViewModel.loginResult.observe(viewLifecycleOwner) { result: String ->
            handleLoginResult(result)
        }

        authViewModel.isLoading.observe(viewLifecycleOwner) { isLoading: Boolean ->
            setLoadingState(isLoading)
        }
    }

    private fun handleLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (!validateInput(email, password)) return

        authViewModel.login(email, password)
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Format email tidak valid", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun handleLoginResult(result: String) {
        if (result.startsWith("Error")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show()
        } else {
            sharedPreferencesHelper.saveJwtToken(result)
            Toast.makeText(context, "Login berhasil", Toast.LENGTH_LONG).show()
            navigateToHome()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        loginButton.isEnabled = !isLoading
        loginButton.text = if (isLoading) "Logging in..." else "Login"
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.nav_home)
    }

    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }
}