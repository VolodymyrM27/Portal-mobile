package com.example.portal.dto.responses

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("code")
    var code: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("email")
    var email: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("imageUrl")
    var imageUrl: String,
    @SerializedName("emailVerified")
    var emailVerified: Boolean,
    @SerializedName("authority")
    var authority: String,
    @SerializedName("provider")
    var provider: String,
    @SerializedName("providerId")
    var providerId: Int
)