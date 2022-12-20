package com.example.portal.dto.responses

import com.google.gson.annotations.SerializedName


data class FridgeProductResponse (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("photo")
        val photo: String,
        @SerializedName("price")
        val price: Double,
        @SerializedName("category")
        val category: FridgeCategoryResponse,
        @SerializedName("capacity")
        val capacity: Double
        )