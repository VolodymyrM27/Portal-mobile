package com.example.portal.auth

import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.entities.dish.DishEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DishApi {

    @GET("/categories/dishes")
    suspend fun getDishCategories(@Header("Authorization") accessToken: String): Response<List<DishCategoryEntity>>

    @GET("/dishes/category/{category-id}")
    suspend fun getDishesByCategory(@Header("Authorization") accessToken: String, @Path("category-id") categoryId: Int): Response<List<DishEntity>>

    companion object {
        fun getApi(): DishApi? {
            return ApiClient.client?.create(DishApi::class.java)
        }
    }
}