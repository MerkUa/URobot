package com.urobot.android.data.NetModel.Request

import com.google.gson.annotations.SerializedName
import com.urobot.android.data.model.UpdateOrCreateScriptsModel

data class RequestBotScripts(
    @field:SerializedName("robot_id")
    val bot_id : Int? = null,
    @field:SerializedName("scripts")
    val scripts: List<UpdateOrCreateScriptsModel>
)
