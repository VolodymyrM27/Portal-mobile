package com.example.portal.dto.responses.dish

import com.google.gson.annotations.SerializedName

data class ProductCategoryEntity (
    @SerializedName("id")
    var Id: Int,
    @SerializedName("title")
    var Title: String,
    @SerializedName("photo")
    var Photo: String
)