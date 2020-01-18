package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CreateWithRobotModel(
    @SerializedName("bot_id")
    @Expose
    var bot_id: Int? = null,
    @SerializedName("robot_id")
    @Expose
    var robotId: Int? = null,
    @SerializedName("messenger_id")
    @Expose
    var messengerId: Int? = null,
    @SerializedName("config")
    @Expose
    var config: Config? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,
    @SerializedName("uuid")
    @Expose
    var uuid: String? = null,
    @SerializedName("web_hook_path")
    @Expose
    var webHookPath: String? = null
)


data class Config (
    @SerializedName("token")
    @Expose
    var token: String? = null
)

