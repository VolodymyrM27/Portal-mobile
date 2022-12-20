package com.example.portal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portal.dto.requests.auth.BaseResponse
import com.example.portal.dto.SignInDTO
import com.example.portal.dto.SignUpDTO
import com.example.portal.repositories.UserRepository
import com.example.portal.dto.requests.LoginRequest
import com.example.portal.dto.requests.SignUpRequest
import com.example.portal.dto.responses.LoginResponse
import com.example.portal.dto.responses.SignUpResponse
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val signUpResult: MutableLiveData<BaseResponse<SignUpResponse>> = MutableLiveData()

    fun loginUser(signInDTO: SignInDTO) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    password = signInDTO.Password,
                    email = signInDTO.Email
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun signUpUser(signUpDTO: SignUpDTO) {

        signUpResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val signUpRequest = SignUpRequest(
                    password = signUpDTO.Password,
                    email = signUpDTO.Email,
                    name = signUpDTO.Name
                )
                val response = userRepo.signUpUser(signUpRequest = signUpRequest)
                if (response?.code() == 200 || response?.isSuccessful == true) {
                    signUpResult.value = BaseResponse.Success(response.body())
                } else {
                    signUpResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                signUpResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}