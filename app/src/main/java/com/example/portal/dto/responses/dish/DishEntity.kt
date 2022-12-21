package com.example.portal.dto.responses.dish

import com.google.gson.annotations.SerializedName

data class DishEntity(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("name")
    var Name: String,
    @SerializedName("photo")
    var Photo: String,
    @SerializedName("category")
    var Category: DishCategoryEntity,
    @SerializedName("caloricity")
    var Caloricity: Float,
    @SerializedName("dishProducts")
    var DishProducts: List<DishProductEntity>
)