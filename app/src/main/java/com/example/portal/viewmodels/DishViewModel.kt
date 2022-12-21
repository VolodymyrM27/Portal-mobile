package com.example.portal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portal.auth.BaseResponse
import com.example.portal.dto.responses.dish.DishEntity
import com.example.portal.repositories.DishRepository
import kotlinx.coroutines.launch

class DishViewModel(application: Application) : AndroidViewModel(application) {

    private val dishRepo = DishRepository()
    val dishByCategoryResult: MutableLiveData<BaseResponse<List<DishEntity>>> = MutableLiveData()

    fun getDishByCategory(accessToken: String, categoryId: Int) {

        dishByCategoryResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = dishRepo.getDishesByCategory(accessToken = accessToken, categoryId = categoryId)
                if (response?.code() == 200) {
                    dishByCategoryResult.value = BaseResponse.Success(response.body())
                } else if (response?.code() == 401 || response?.code() == 403) {
                    dishByCategoryResult.value = BaseResponse.Unauthorized(response?.message())
                }
                else {
                    dishByCategoryResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                dishByCategoryResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}