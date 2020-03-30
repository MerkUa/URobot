package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GetContactsModel (

    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null,
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null,
    @SerializedName("photo")
    @Expose
    var photo: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("messenger_id")
    @Expose
    var messengerId: Int = 0,
    @SerializedName("last_message")
    @Expose
    var lastMessage: LastMessage? = null

)


data class LastMessage(
    @SerializedName("data")
    @Expose
    var text: String = "",
    @SerializedName("created_at")
    @Expose
    var time: String? = null
)