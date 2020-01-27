package com.urobot.droid.data.model

data class BotContentItem(
    var id: String?,
    var parent_id: String?,
    var name: String?,
    var action: Int?,
    var isEmpty: Boolean,
    var description: String,
    var list_buttons: ArrayList<ServiceButtons>?
)

data class ServiceButtons(var id: Int?)