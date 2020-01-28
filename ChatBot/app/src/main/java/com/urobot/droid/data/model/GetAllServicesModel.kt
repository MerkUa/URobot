package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Datum (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("service_id")
    @Expose
    var serviceId: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("working_hours_from")
    @Expose
    var workingHoursFrom: String? = null,
    @SerializedName("working_hours_to")
    @Expose
    var workingHoursTo: String? = null,
    @SerializedName("break")
    @Expose
    var `break`: Int? = null,
    @SerializedName("session_duration")
    @Expose
    var sessionDuration: Int? = null,
    @SerializedName("working_days")
    @Expose
    var workingDays: List<String>? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
)

data class GetAllServicesModel (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("bot_id")
    @Expose
    var botId: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("type_id")
    @Expose
    var typeId: Int? = null,
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null
    )