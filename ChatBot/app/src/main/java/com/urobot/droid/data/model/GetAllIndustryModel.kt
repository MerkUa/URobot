package com.urobot.droid.data.model

import com.google.gson.annotations.SerializedName

data class GetAllIndustryModel (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)
