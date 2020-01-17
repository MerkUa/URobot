package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName

class RequestCreateBot(
    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("messenger_id")
    val messengerId: String? = null

) {
    companion object {
        const val TELEGRAM = "1"
        const val VIBER = "2"
        const val FACEBOOK = "3"
        const val INSTAGRAM = "4"
        const val VK = "5"
        const val WHATSAPP = "6"
    }

}
