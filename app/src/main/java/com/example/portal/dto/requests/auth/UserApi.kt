package com.example.portal.dto.requests.auth

import com.example.portal.dto.requests.LoginRequest
import com.example.portal.dto.requests.SignUpRequest
import com.example.portal.dto.responses.FridgeResponse
import com.example.portal.dto.responses.LoginResponse
import com.example.portal.dto.responses.SignUpResponse
import com.example.portal.dto.responses.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/signup")
    suspend fun signUpUser(@Body signupRequest: SignUpRequest): Response<SignUpResponse>

    @GET("/user/me")
    suspend fun getCurrentUser(@Header("Authorization") accessToken: String): Response<UserResponse>

    @GET("/fridge")
    suspend fun getFridge(@Header("Authorization") token: String): Response<List<FridgeResponse>>

    @PUT("/fridge/delete/{item-id}/amount/{amount}")
    suspend fun deleteFromFridge(@Header("Authorization") token: String, @Path("item-id") id: Int, @Path("amount") amount: Int): Response<List<FridgeResponse>>

    @PUT("/fridge/{item-id}/setamount/{amount}")
    suspend fun updateFridgeAmount(@Header("Authorization") token: String, @Path("item-id") id: Int, @Path("amount") amount: Int): Response<List<FridgeResponse>>


    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }
}