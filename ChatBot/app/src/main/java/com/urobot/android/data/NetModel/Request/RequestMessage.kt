package com.urobot.android.data.NetModel.Request

import com.google.gson.annotations.SerializedName

data class RequestMessage (
    @field:SerializedName("contact_id")
    val id : Int? = null,
    @field:SerializedName("data")
    val data:String? = null,
    @field:SerializedName("type")
    val type : String? = null
)

enum class Type(val type :String){
    Text("text"),
    Image("image"),
    File("file"),
    AudioFile("audio"),
    VideoFile("video")
}
