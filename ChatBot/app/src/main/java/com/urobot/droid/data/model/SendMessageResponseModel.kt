package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendMessageResponseModel(
    @SerializedName("id")
    @Expose
    var id: Int? = null
)

