package com.example.portal.google

import androidx.lifecycle.ViewModel
import com.example.portal.google.GoogleUserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _user: MutableStateFlow<GoogleUserModel?> = MutableStateFlow(null)
    val user: StateFlow<GoogleUserModel?> = _user

    suspend fun signIn(email: String, displayName: String) {
        delay(2000) // Simulating network call
        _user.value = GoogleUserModel(email, displayName)
    }
}