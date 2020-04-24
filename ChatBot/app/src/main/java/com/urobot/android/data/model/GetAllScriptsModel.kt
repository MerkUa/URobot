package com.urobot.android.data.model


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
    var serviceId: Long? = null,
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
    var uid: Long? = null,
    @SerializedName("empty")
    @Expose
    var empty: Boolean? = null,
    @SerializedName("parent_uid")
    @Expose
    var parentUid: Long? = null,
    @SerializedName("service_id")
    @Expose
    var serviceId: Long? = null,
    @SerializedName("level")
    @Expose
    var level: Int? = null,
    @SerializedName("data")
    @Expose
    var data: String? = null,
    @SerializedName("text")
    @Expose
    var text: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("delay")
    @Expose
    var delay: Int? = null,
//    @SerializedName("messages")
//    @Expose
//    var messages: List<MessageData>? = null,
    @SerializedName("buttons")
    @Expose
    var buttons: List<ButtonMenu>? = null

    )

data class MessageData (
    @SerializedName("bot_id")
    @Expose
    var botId: Int? = null,
    @SerializedName("uid")
    @Expose
    var uid: Int? = null,
    @SerializedName("parent_uid")
    @Expose
    var parentUid: Int? = null,
    @SerializedName("level")
    @Expose
    var level: Int? = null,
    @SerializedName("data")
    @Expose
    var data: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("delay")
    @Expose
    var delay: Int? = null,
//    @SerializedName("messages")
//    @Expose
//    var messages: List<MessageData>? = null,
    @SerializedName("buttons")
    @Expose
    var buttons: List<ButtonMenu>? = null


)