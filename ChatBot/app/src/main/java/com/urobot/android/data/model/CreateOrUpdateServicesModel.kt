package com.urobot.android.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CreateOrUpdateServicesModel (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

)