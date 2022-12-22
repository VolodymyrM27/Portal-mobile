package com.example.portal.auth

import com.example.portal.entities.ProductCategoryEntity
import com.example.portal.entities.ProductEntity
import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.entities.dish.DishEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProductApi {

    @GET("/categories/products")
    suspend fun getProductCategories(@Header("Authorization") accessToken: String): Response<List<ProductCategoryEntity>>

    @GET("/products/category/{category-id}")
    suspend fun getProductsByCategory(@Header("Authorization") accessToken: String, @Path("category-id") categoryId: Int): Response<List<ProductEntity>>

    companion object {
        fun getApi(): ProductApi? {
            return ApiClient.client?.create(ProductApi::class.java)
        }
    }
}