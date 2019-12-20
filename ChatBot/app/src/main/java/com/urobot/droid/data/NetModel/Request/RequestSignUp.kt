package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName

class RequestSignUp(
        @field:SerializedName("email")
        val userName: String? = null,

        @field:SerializedName("password")
        val password: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null,

        @field:SerializedName("last_name")
        val lastName: String? = null

)
