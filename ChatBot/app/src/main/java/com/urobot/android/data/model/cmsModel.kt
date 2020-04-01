package com.urobot.android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class cmsModel(
    @SerializedName("id")
    @Expose
    var id: Int = 100,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("key")
    @Expose
    var key: String? = null,
    @SerializedName("added")
    @Expose
    var added: Boolean = false,
    @SerializedName("active")
    @Expose
    var active: Boolean = false


)

enum class cmsType(val type_id: Int) {
    BillingAccount(0),
    OneC(1),
    Amo(2),
    Unknown(100);


    companion object {
        fun fromValue(x: Int): cmsType {
            return when (x) {
                0 -> BillingAccount
                1 -> OneC
                2 -> Amo

                else -> Unknown
            }
        }
    }
}


