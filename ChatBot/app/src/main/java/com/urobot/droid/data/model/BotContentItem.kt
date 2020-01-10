package com.urobot.droid.data.model

data class BotContentItem(
    var id: Int?, var parent_id: Int?, var name: String?, var description: String,
    var list_buttons: List<ServiceButtons>
)

data class ServiceButtons(var id: Int?)