package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName

data class RequestMessage (
    @field:SerializedName("contact_id")
    val id : Int? = null,
    @field:SerializedName("message")
    val message:String? = null
)