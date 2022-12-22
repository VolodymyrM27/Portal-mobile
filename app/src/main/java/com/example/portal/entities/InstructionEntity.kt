package com.example.portal.entities

import com.google.gson.annotations.SerializedName

data class InstructionEntity(
    @SerializedName("description")
    var Description: String,
    @SerializedName("step")
    var Step: Int
)