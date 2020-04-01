package com.urobot.android.data.NetModel.Request

import com.google.gson.annotations.SerializedName
import com.urobot.android.data.model.OnlineRecordModel

data class RequestBotCalendarService(

    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("type_id")
    val type_id: Int,
    @field:SerializedName("data")
    val data: OnlineRecordModel?
)