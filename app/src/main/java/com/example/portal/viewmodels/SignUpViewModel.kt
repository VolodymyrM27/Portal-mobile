package com.example.portal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portal.dto.requests.auth.BaseResponse
import com.example.portal.repositories.UserRepository
import com.example.portal.dto.requests.SignUpRequest
import com.example.portal.dto.responses.SignUpResponse
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo = UserRepository()
    val signUpResult: MutableLiveData<BaseResponse<SignUpResponse>> = MutableLiveData()

    fun signUpUser(email: String, pwd: String, name: String) {

        signUpResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val signUpRequest = SignUpRequest(
                    password = pwd,
                    email = email,
                    name = name
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