package com.example.portal.repositories

import com.example.portal.auth.UserApi
import com.example.portal.requests.LoginRequest
import com.example.portal.requests.SignUpRequest
import com.example.portal.responses.LoginResponse
import com.example.portal.responses.SignUpResponse
import com.example.portal.responses.UserResponse
import retrofit2.Response

class UserRepository {

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }

    suspend fun signUpUser(signUpRequest: SignUpRequest): Response<SignUpResponse>? {
        return UserApi.getApi()?.signUpUser(signupRequest = signUpRequest)
    }

    suspend fun getUserInformation(accessToken : String): Response<UserResponse>? {
        return UserApi.getApi()?.getCurrentUser(accessToken = accessToken)
    }
}