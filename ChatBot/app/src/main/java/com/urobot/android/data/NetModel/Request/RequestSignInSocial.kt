package com.urobot.android.data.NetModel.Request

import com.google.gson.annotations.SerializedName

class RequestSignInSocial(
        @field:SerializedName("type")
        val type: String? = null,

        @field:SerializedName("token")
        val token: String? = null,

        @field:SerializedName("secret")
        val secret: String? = null

) {
    companion object {
        const val FACEBOOK = "Facebook"
        const val TWITTER = "Twitter"
        const val GOOGLE = "Google"
    }
}
