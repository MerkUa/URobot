package com.urobot.android.data.model

import com.google.gson.annotations.SerializedName

data class GetRobotModel(
    @field:SerializedName("id")
    var id: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("updated_at")
    var updatedAt: String? = null,

    @field:SerializedName("created_at")
    var createdAt: String? = null
)