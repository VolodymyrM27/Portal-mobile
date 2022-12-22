package com.example.portal.entities.dish

import com.example.portal.entities.DishProductEntity
import com.google.gson.annotations.SerializedName

data class DishProductEntity(
    @SerializedName("amount")
    var Amount: Int,
    @SerializedName("product")
    var Product: DishProductEntity
)