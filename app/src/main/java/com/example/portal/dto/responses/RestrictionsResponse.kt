package com.example.portal.dto.responses

import com.google.gson.annotations.SerializedName

data class RestrictionsResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String
        )