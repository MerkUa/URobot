package com.urobot.android.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetMessageModel(
    @SerializedName("current_page")
    @Expose
    var currentPage: Int? = null,
    @SerializedName("data")
    @Expose
    var data: List<DatumMessage>? = null,
    @SerializedName("first_page_url")
    @Expose
    var firstPageUrl: String? = null,
    @SerializedName("from")
    @Expose
    var from: Int? = null,
    @SerializedName("last_page")
    @Expose
    var lastPage: Int? = null,
    @SerializedName("last_page_url")
    @Expose
    var lastPageUrl: String? = null,
    @SerializedName("next_page_url")
    @Expose
    var nextPageUrl: Any? = null,
    @SerializedName("path")
    @Expose
    var path: String? = null,
    @SerializedName("per_page")
    @Expose
    var perPage: String? = null,
    @SerializedName("prev_page_url")
    @Expose
    var prevPageUrl: Any? = null,
    @SerializedName("to")
    @Expose
    var to: Int? = null,
    @SerializedName("total")
    @Expose
    var total: Int? = null
)

data class DatumMessage (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("sender_id")
    @Expose
    var senderId: String? = null,
    @SerializedName("data")
    @Expose
    var data: String? = null,
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,
    @SerializedName("type")
    @Expose
    var type : String? = null
)

