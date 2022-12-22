package com.example.portal.repositories

import com.example.portal.auth.DishApi
import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.entities.dish.DishEntity
import retrofit2.Response

class DishRepository {
    suspend fun getDishCategories(accessToken: String): Response<List<DishCategoryEntity>>? {
        return DishApi.getApi()?.getDishCategories(accessToken = accessToken)
    }

    suspend fun getDishesByCategory(accessToken: String, categoryId: Int): Response<List<DishEntity>>? {
        return DishApi.getApi()?.getDishesByCategory(accessToken = accessToken, categoryId = categoryId)
    }
}