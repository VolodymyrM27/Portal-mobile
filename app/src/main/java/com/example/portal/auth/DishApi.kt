package com.example.portal.auth

import com.example.portal.dto.requests.LoginRequest
import com.example.portal.dto.requests.SignUpRequest
import com.example.portal.dto.responses.LoginResponse
import com.example.portal.dto.responses.SignUpResponse
import com.example.portal.dto.responses.UserResponse
import com.example.portal.dto.responses.dish.DishEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DishApi {

    @GET("/dishes/category/{category-id}")
    suspend fun getDishesByCategory(@Header("Authorization") accessToken: String, @Path("category-id") categoryId: Int): Response<List<DishEntity>>

    companion object {
        fun getApi(): DishApi? {
            return ApiClient.client?.create(DishApi::class.java)
        }
    }
}