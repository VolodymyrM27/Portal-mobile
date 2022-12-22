package com.example.portal.repositories

import com.example.portal.dto.requests.auth.UserApi
import com.example.portal.dto.requests.LoginRequest
import com.example.portal.dto.requests.SignUpRequest
import com.example.portal.dto.responses.*
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path

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
    suspend fun getFridgeItems(accessToken: String): Response<List<FridgeResponse>>? {
        return UserApi.getApi()?.getFridge(accessToken)
    }

    suspend fun deleteFromFridge(accessToken: String, id: Int, amount: Int): Response<List<FridgeResponse>>? {
        return UserApi.getApi()?.deleteFromFridge(accessToken, id, amount)
    }

    suspend fun updateFridgeAmount(token: String, id: Int, amount: Int): Response<List<FridgeResponse>>? {
        return UserApi.getApi()?.updateFridgeAmount(token, id, amount)
    }

    suspend fun getRestrictions(token: String): Response<List<RestrictionsResponse>>? {
        return UserApi.getApi()?.getRestrictions(token)
    }
    suspend fun deleteRestriction(accessToken: String, id: Int): Response<List<RestrictionsResponse>>? {
        return UserApi.getApi()?.deleteRestriction(accessToken, id)
    }
}