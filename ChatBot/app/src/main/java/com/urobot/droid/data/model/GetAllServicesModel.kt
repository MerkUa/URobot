package com.urobot.droid.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Datum (
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
    var `break`: String? = null,
    @SerializedName("session_duration")
    @Expose
    var sessionDuration: String? = null,
    @SerializedName("working_days")
    @Expose
    var workingDays: List<String>? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,
    //for payService
    @SerializedName("card_number")
    @Expose
    var cardNumber: String? = null,

    @SerializedName("card_name")
    @Expose
    var card_name: String? = null,

    @SerializedName("month")
    @Expose
    var month: String? = null,

    @SerializedName("year")
    @Expose
    var year: String? = null,

    @SerializedName("cvv")
    @Expose
    var cvv: String? = null,

    @SerializedName("payment_types")
    @Expose
    var payment_types: List<String>? = null
    ):Parcelable

@Parcelize
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
    var data: Datum? = null
    ):Parcelable