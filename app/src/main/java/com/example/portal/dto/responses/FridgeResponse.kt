package com.example.portal.dto.responses

import com.google.gson.annotations.SerializedName

data class FridgeResponse (
    @SerializedName("product")
    val product: FridgeProductResponse,
    @SerializedName("amount")
    val amount: Int
        )