package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName

class AddBotConfig(
    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("code")
    val code: String? = null
)
