package com.example.portal.repositories

import com.example.portal.auth.DishApi
import com.example.portal.dto.responses.dish.DishEntity
import retrofit2.Response

class DishRepository {
    suspend fun getDishesByCategory(accessToken: String, categoryId: Int): Response<List<DishEntity>>? {
        return DishApi.getApi()?.getDishesByCategory(accessToken = accessToken, categoryId = categoryId)
    }
}