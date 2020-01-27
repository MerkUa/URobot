package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class Action (
    @SerializedName("script_id")
    @Expose
    var scriptId: Int? = null
)

 data class Button (
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("script_id")
    @Expose
    var scriptId: Int? = null,
    @SerializedName("service_id")
    @Expose
    var serviceId: Int? = null
)

data class ButtonMenu (
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("script_id")
    @Expose
    var scriptId: Int? = null,
    @SerializedName("service_id")
    @Expose
    var serviceId: Int? = null,
    @SerializedName("action")
    @Expose
    var action: Action? = null
    )

data class GetAllScriptsModel (
    @SerializedName("bot_id")
    @Expose
    var botId: Int? = null,
    @SerializedName("uid")
    @Expose
    var uid: Int? = null,
    @SerializedName("parent_uid")
    @Expose
    var parentUid: Int? = null,
    @SerializedName("messages")
    @Expose
    var messages: List<MessageData>? = null,
    @SerializedName("buttons")
    @Expose
    var buttons: List<ButtonMenu>? = null

    )

data class MessageData (
    @SerializedName("data")
    @Expose
    var data: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("delay")
    @Expose
    var delay: Int? = null,
    @SerializedName("buttons")
    @Expose
    var buttons: List<Button>? = null

)