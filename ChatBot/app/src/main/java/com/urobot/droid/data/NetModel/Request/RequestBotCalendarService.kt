package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName
import com.urobot.droid.data.model.OnlineRecordModel

data class RequestBotCalendarService(
    @field:SerializedName("bot_id")
    val bot_id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("type_id")
    val type_id: Int,
    @field:SerializedName("data")
    val data: OnlineRecordModel?
)