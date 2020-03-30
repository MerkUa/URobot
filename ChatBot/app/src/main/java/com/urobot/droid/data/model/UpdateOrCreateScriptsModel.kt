package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateOrCreateScriptsModel (
    @SerializedName("text")
    @Expose
    var data: String? = null,
    @SerializedName("delay")
    @Expose
    var delay: Int? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("parent_uid")
    @Expose
    var parentUid: Long? = null,
    @SerializedName("level")
    @Expose
    var level: Int? = null,
    @SerializedName("uid")
    @Expose
    var uid: Long? = null,
    @SerializedName("service_id")
    @Expose
    var serviceId: Long? = null,
    @SerializedName("empty")
    @Expose
    var empty: Boolean? = null,
    @SerializedName("buttons")
    @Expose
    var buttons: List<ServiceButtons>? = null
)


data class Message (
    @SerializedName("data")
    @Expose
    var data: String? = null,
    @SerializedName("delay")
    @Expose
    var delay: Int? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("buttons")
    @Expose
    var buttons: List<Button>? = null
)