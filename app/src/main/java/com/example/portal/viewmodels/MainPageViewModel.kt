package com.example.portal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portal.auth.BaseResponse
import com.example.portal.auth.SessionManager
import com.example.portal.repositories.UserRepository
import com.example.portal.requests.LoginRequest
import com.example.portal.responses.LoginResponse
import com.example.portal.responses.UserResponse
import kotlinx.coroutines.launch

class MainPageViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val currentUserResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()

    fun getCurrentUser(accessToken: String) {
        currentUserResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = userRepo.getUserInformation(accessToken)
                if (response?.code() == 200) {
                    currentUserResult.value = BaseResponse.Success(response.body())
                } else {
                    currentUserResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                currentUserResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}