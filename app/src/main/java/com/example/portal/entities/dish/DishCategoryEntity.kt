package com.example.portal.entities.dish

import com.google.gson.annotations.SerializedName

data class DishCategoryEntity(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("title")
    var Title: String,
    @SerializedName("photo")
    var Photo: String
)