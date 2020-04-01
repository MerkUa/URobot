package com.urobot.android.NetModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ResponseLoginModel(

        @field:SerializedName("id")
        val userId: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null,

        @field:SerializedName("last_name")
        val lastName: String? = null,

        @field:SerializedName("email")
        val email: String? = null,

        @field:SerializedName("photo")
        val photo: String? = null,

        @field:SerializedName("token")
        val token: String? = null,

        @field:SerializedName("subscription_id")
        val subscriptionId: String? = null,

        @field:SerializedName("phone")
        val phone: String? = null,

        @SerializedName("industries")
        @Expose
        var industries: List<Industries>? = null

)

data class Industries (
        @SerializedName("id")
        @Expose
        var id: Int? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null
)


