package com.urobot.android.data.NetModel.Request

import com.google.gson.annotations.SerializedName

class RequestLogin(
        @field:SerializedName("email")
        val userName: String? = null,

        @field:SerializedName("password")
        val password: String? = null

)
