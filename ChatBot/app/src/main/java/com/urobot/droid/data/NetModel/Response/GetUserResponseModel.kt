package com.urobot.droid.data.NetModel.Response

import com.google.gson.annotations.SerializedName

data class GetUserResponseModel(

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("userName")
        val userName: String? = null,

        @field:SerializedName("email")
        val email: String? = null,

        @field:SerializedName("isActive")
        val isActive: Boolean? = null,

        @field:SerializedName("fullName")
        val fullName: String? = null,

        @field:SerializedName("cellPhone")
        val cellPhone: String? = null,

        @field:SerializedName("photoUrl")
        val photoUrl: String? = null

)