package com.example.portal.repositories

import com.example.portal.auth.DishApi
import com.example.portal.auth.ProductApi
import com.example.portal.entities.ProductCategoryEntity
import com.example.portal.entities.ProductEntity
import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.entities.dish.DishEntity
import retrofit2.Response

class ProductRepository {
    suspend fun getProductCategories(accessToken: String): Response<List<ProductCategoryEntity>>? {
        return ProductApi.getApi()?.getProductCategories(accessToken = accessToken)
    }

    suspend fun getProductsByCategory(accessToken: String, categoryId: Int): Response<List<ProductEntity>>? {
        return ProductApi.getApi()?.getProductsByCategory(accessToken = accessToken, categoryId = categoryId)
    }
}