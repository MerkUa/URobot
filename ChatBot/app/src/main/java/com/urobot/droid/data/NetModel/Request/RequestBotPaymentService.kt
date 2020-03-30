package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName
import com.urobot.droid.data.model.PaymentModel

data class RequestBotPaymentService (

    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("type_id")
    val type_id: Int,
    @field:SerializedName("data")
    val data : PaymentModel?
)