package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Action (
    @SerializedName("step_id")
    @Expose
    // for new feature
    // for send service id or stepId
    var stepId: Int? = null
)

data class MessageScript (
    @SerializedName("main")
    @Expose
    // set 0 or 1
    // 0 is main
    // main message is first
    var main: Int? = null,
    @SerializedName("type")
    @Expose
    // text or image
    // to find type of message
    var type: String? = null,
    @SerializedName("data")
    @Expose
    // data is data of message
    var data: String? = null,

    // set 0 sec
    @SerializedName("delay")
    @Expose
    var delay: Int? = null,

    //type of keyBoard
    // list of but
    @SerializedName("keyboard")
    @Expose
    var keyboard: List<Any>? = null
)

data class UpdateScriptsModel (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("messages")
    @Expose
    var messages: List<MessageScript>? = null,
    @SerializedName("key")
    @Expose
    var key: Int? = null,
    @SerializedName("action")
    @Expose
    var action: Action? = null
)
