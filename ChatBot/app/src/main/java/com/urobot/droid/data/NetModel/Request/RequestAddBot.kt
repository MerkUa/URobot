package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName

class RequestAddBot(
    @field:SerializedName("robot_id")
    val robot_id: String? = null,

    @field:SerializedName("messenger_id")
    val messenger_id: Int? = null,

    @field:SerializedName("config")
    val config: AddBotConfig? = null

)


