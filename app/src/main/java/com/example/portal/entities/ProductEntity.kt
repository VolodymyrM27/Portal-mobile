package com.example.portal.entities

import com.google.gson.annotations.SerializedName

data class ProductEntity(
    @SerializedName("id")
    var Id: Int,
    @SerializedName("name")
    var Name: String,
    @SerializedName("photo")
    var Photo: String,
    @SerializedName("price")
    var Price: Double,
    @SerializedName("category")
    var Category: ProductCategoryEntity,
    @SerializedName("capacity")
    var Capacity: Double,
    )