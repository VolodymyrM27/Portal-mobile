package com.example.portal.dto.responses

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName

data class FridgeCategoryResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("photo")
    val photo: String
        )