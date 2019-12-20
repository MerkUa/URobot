package com.urobot.droid.NetModel

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
        val phone: String? = null


)


