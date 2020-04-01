package com.urobot.android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BotContentItem(
    var id: Long?,
    var parent_id: Long?,
    var level: Int?,
    var name: String?,
    var action: Long?,
    var isEmpty: Boolean,
    var description: String?,
    var image: String?,
    var list_buttons: ArrayList<ServiceButtons>?
)

data class ServiceButtons(
    @SerializedName("service_id")
    @Expose
    var id: Long?,
    @SerializedName("name")
    @Expose
    var name: String?
)