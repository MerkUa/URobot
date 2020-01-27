package com.urobot.droid.data.NetModel.Request

import com.google.gson.annotations.SerializedName
import com.urobot.droid.data.model.UpdateOrCreateScriptsModel

data class RequestBotScripts(
    @field:SerializedName("bot_id")
    val bot_id : Int? = null,
    @field:SerializedName("scripts")
    val scripts: List<UpdateOrCreateScriptsModel> = emptyList()
)
