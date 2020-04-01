package com.urobot.android.data.model

import com.google.gson.annotations.SerializedName

data class UpdateBotCalendarService(
    @field:SerializedName("service_id")
    val service_id: Int,
//    @field:SerializedName("bot_id")
//    val bot_id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("data")
    val data: OnlineRecordModel?,
    @field:SerializedName("type_id")
    val type_id: Int
)
