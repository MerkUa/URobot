package com.urobot.droid.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdsModel(
    @field:SerializedName("id")
    val id: String
):Parcelable
