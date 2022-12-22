package com.example.portal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portal.auth.BaseResponse
import com.example.portal.entities.ProductCategoryEntity
import com.example.portal.entities.ProductEntity
import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.entities.dish.DishEntity
import com.example.portal.repositories.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val dishRepo = ProductRepository()
    val productCategoriesResult: MutableLiveData<BaseResponse<List<ProductCategoryEntity>>> =
        MutableLiveData()
    val productByCategoryResult: MutableLiveData<BaseResponse<List<ProductEntity>>> = MutableLiveData()

    fun getProductCategories(accessToken: String) {
        productCategoriesResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = dishRepo.getProductCategories(accessToken = accessToken)
                if (response?.code() == 200) {
                    productCategoriesResult.value =
                        BaseResponse.Success(response.body()?.sortedBy { x -> x.Title })
                } else if (response?.code() == 401 || response?.code() == 403) {
                    productCategoriesResult.value = BaseResponse.Unauthorized(response?.message())
                } else {
                    productCategoriesResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                productCategoriesResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getProductsByCategory(accessToken: String, categoryId: Int) {
        productByCategoryResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response =
                    dishRepo.getProductsByCategory(accessToken = accessToken, categoryId = categoryId)
                if (response?.code() == 200) {
                    productByCategoryResult.value =
                        BaseResponse.Success(response.body()?.sortedBy { x -> x.Name })
                } else if (response?.code() == 401 || response?.code() == 403) {
                    productByCategoryResult.value = BaseResponse.Unauthorized(response?.message())
                } else {
                    productByCategoryResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                productByCategoryResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}