package com.urobot.droid.data.model

data class BotContentItem(
    var id: Int?,
    var parent_id: Int?,
    var name: String?,
    var action: Int?,
    var isEmpty: Boolean,
    var description: String,
    var list_buttons: ArrayList<ServiceButtons>?
)

data class ServiceButtons(var id: Int?)