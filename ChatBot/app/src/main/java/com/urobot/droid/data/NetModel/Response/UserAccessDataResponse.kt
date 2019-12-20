package com.urobot.droid.data.NetModel.Response

import com.google.gson.annotations.SerializedName

data class UserAccessDataResponse(
        @field:SerializedName("accessToken")
        val accessToken: String? = null,

        @field:SerializedName("appId")
        val appId: String? = null,

        @field:SerializedName("appPassword")
        val appPassword: String? = null,

        @field:SerializedName("clientAccessToken")
        val clientAccessToken: String? = null,

        @field:SerializedName("pageAccessToken")
        val pageAccessToken: String? = null,

        @field:SerializedName("userAccessToken")
        val userAccessToken: String? = null
)