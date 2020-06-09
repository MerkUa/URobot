package com.urobot.android.data.model

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
    val month: String,
    @field:SerializedName("year")
    val year: String,
    @field:SerializedName("cvv")
    val cvv: String,
    @field:SerializedName("price")
    val price: String,
    @field:SerializedName("payment_types")
    val payment_types: List<String>
) : Parcelable

enum class PaymentTypes(val type :String){
    CreditCard("credit_card"),
    Paypal("paypal")
}
