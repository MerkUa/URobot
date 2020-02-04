package com.urobot.droid.NetModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.urobot.droid.data.model.GetAllIndustryModel
import com.urobot.droid.db.Industry


data class ResponseLoginModel(

        @field:SerializedName("id")
        val userId: String? = null,

        @field:SerializedName("first_name")
        val firstName: String? = null,

        @field:SerializedName("last_name")
        val lastName: String? = null,

        @field:SerializedName("email")
        val email: String? = null,

        @field:SerializedName("photo")
        val photo: String? = null,

        @field:SerializedName("token")
        val token: String? = null,

        @field:SerializedName("subscription_id")
        val subscriptionId: String? = null,

        @field:SerializedName("phone")
        val phone: String? = null,

        @SerializedName("industries")
        @Expose
        var industries: List<Industry>? = null

)

//class Example {
//        @SerializedName("id")
//        @Expose
//        var id: Int? = null
//        @SerializedName("first_name")
//        @Expose
//        var firstName: String? = null
//        @SerializedName("last_name")
//        @Expose
//        var lastName: String? = null
//        @SerializedName("patronymic_name")
//        @Expose
//        var patronymicName: String? = null
//        @SerializedName("email")
//        @Expose
//        var email: String? = null
//        @SerializedName("photo")
//        @Expose
//        var photo: String? = null
//        @SerializedName("phone")
//        @Expose
//        var phone: String? = null
//        @SerializedName("auth_type_id")
//        @Expose
//        var authTypeId: Int? = null
//        @SerializedName("subscription_id")
//        @Expose
//        var subscriptionId: Int? = null
//
//        @SerializedName("token")
//        @Expose
//        var token: String? = null
//
//}
//
//class Industry {
//        @SerializedName("id")
//        @Expose
//        var id: Int? = null
//        @SerializedName("name")
//        @Expose
//        var name: String? = null
//
//}


