package com.urobot.droid.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentModel(
    @field:SerializedName("card_number")
    val card_number: String,
    @field:SerializedName("card_name")
    val card_name: String,
    @field:SerializedName("month")
    val month: Int,
    @field:SerializedName("year")
    val year: Int,
    @field:SerializedName("cvv")
    val cvv: String,
    @field:SerializedName("payment_types")
    val payment_types: List<String>
) : Parcelable
