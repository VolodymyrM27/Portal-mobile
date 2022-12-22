package com.example.portal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portal.auth.BaseResponse
import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.entities.dish.DishEntity
import com.example.portal.repositories.DishRepository
import kotlinx.coroutines.launch

class DishViewModel(application: Application) : AndroidViewModel(application) {

    private val dishRepo = DishRepository()
    val dishCategoriesResult: MutableLiveData<BaseResponse<List<DishCategoryEntity>>> =
        MutableLiveData()
    val dishesByCategoryResult: MutableLiveData<BaseResponse<List<DishEntity>>> = MutableLiveData()

    fun getDishCategories(accessToken: String) {
        dishCategoriesResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = dishRepo.getDishCategories(accessToken = accessToken)
                if (response?.code() == 200) {
                    dishCategoriesResult.value =
                        BaseResponse.Success(response.body()?.sortedBy { x -> x.Title })
                } else if (response?.code() == 401 || response?.code() == 403) {
                    dishCategoriesResult.value = BaseResponse.Unauthorized(response?.message())
                } else {
                    dishCategoriesResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                dishCategoriesResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getDishesByCategory(accessToken: String, categoryId: Int) {
        dishesByCategoryResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response =
                    dishRepo.getDishesByCategory(accessToken = accessToken, categoryId = categoryId)
                if (response?.code() == 200) {
                    dishesByCategoryResult.value =
                        BaseResponse.Success(response.body()?.sortedBy { x -> x.Name })
                } else if (response?.code() == 401 || response?.code() == 403) {
                    dishesByCategoryResult.value = BaseResponse.Unauthorized(response?.message())
                } else {
                    dishesByCategoryResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                dishesByCategoryResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}