package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName

class RequestUpdateUser(

        @field:SerializedName("user_id")
        val userId: String? = null,

        @field:SerializedName("patronymic_name")
        val patronymicName: String? = null,

        @field:SerializedName("phone")
        val phone: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null,

        @field:SerializedName("last_name")
        val lastName: String? = null

)
