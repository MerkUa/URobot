package com.urobot.android.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OnlineRecordModel(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("working_hours_from")
    val working_hours_from: String,
    @field:SerializedName("working_hours_to")
    val working_hours_to: String,
    @field:SerializedName("break")
    val break_count: String,
    @field:SerializedName("session_duration")
    val session_duration: String,
    @field:SerializedName("working_days")
    val working_days: List<String>
) : Parcelable

