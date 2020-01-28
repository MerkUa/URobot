package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateOrCreateScriptsModel (
    @SerializedName("buttons")
    @Expose
    var buttons: List<Button>? = null,
    @SerializedName("messages")
    @Expose
    var messages: List<Message>? = null,
    @SerializedName("parent_uid")
    @Expose
    var parentUid: Int? = null,
    @SerializedName("level")
    @Expose
    var level: Int? = null,
    @SerializedName("uid")
    @Expose
    var uid: Int? = null
)


data class Message (
    @SerializedName("data")
    @Expose
    var data: String? = null,
    @SerializedName("delay")
    @Expose
    var delay: Int? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("buttons")
    @Expose
    var buttons: List<Button>? = null
)