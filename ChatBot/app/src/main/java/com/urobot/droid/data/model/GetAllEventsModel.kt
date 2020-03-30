package com.urobot.droid.data.model

import com.google.gson.annotations.SerializedName

data class GetAllEventsModel(
    @field:SerializedName("contact_id")
    val id: Int,
    @field:SerializedName("first_name")
    val firstName: String,
    @field:SerializedName("last_name")
    val lastName: String,
    @field:SerializedName("records")
    val records: ArrayList<recordsModel>
)

data class recordsModel(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("service_id")
    val serviceId: String,
    @field:SerializedName("date")
    val date: String,
    @field:SerializedName("time")
    val time: String
)
