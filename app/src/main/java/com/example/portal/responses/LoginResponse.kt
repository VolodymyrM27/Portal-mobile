package com.example.portal.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("code")
    var code: Int,
//    @SerializedName("data")
//    var `data`: Data,
//    @SerializedName("id")
//    var id: String,
//    @SerializedName("message")
//    var message: String
    @SerializedName("accessToken")
    var token: String,
    @SerializedName("tokenType")
    var name: String
) {
//    data class Data(
//        @SerializedName("accessToken")
//        var token: String,
//        @SerializedName("tokenType")
//        var name: String,
//    )
}