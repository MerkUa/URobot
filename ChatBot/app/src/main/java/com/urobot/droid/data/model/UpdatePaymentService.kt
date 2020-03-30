package com.urobot.droid.data.model

import com.google.gson.annotations.SerializedName

data class UpdatePaymentService (
    @field:SerializedName("service_id")
    val service_id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("data")
    val data : PaymentModel?
)