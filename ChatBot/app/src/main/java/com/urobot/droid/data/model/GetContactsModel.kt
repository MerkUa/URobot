package com.urobot.droid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GetContactsModel (

    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null,
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null,
    @SerializedName("photo")
    @Expose
    var photo: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

)