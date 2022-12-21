package com.example.portal.dto.responses.dish

import com.google.gson.annotations.SerializedName

data class DishProductEntity(
    @SerializedName("amount")
    var Amount: Int,
    @SerializedName("product")
    var Product: ProductEntity
)