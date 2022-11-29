package com.example.portal.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    var code: Int,
    @SerializedName("accessToken")
    var token: String,
    @SerializedName("tokenType")
    var name: String
) {
}