package com.example.portal.responses

import com.google.gson.annotations.SerializedName

data class SignUpResponse (
    @SerializedName("code")
    var code: Int,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("message")
    var message: String,
)