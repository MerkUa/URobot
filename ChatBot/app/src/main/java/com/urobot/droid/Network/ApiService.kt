package com.urobot.droid.Network

import io.reactivex.Observable
import com.urobot.droid.NetModel.ResponseLoginModel
import com.urobot.droid.data.NetModel.Request.RequestLogin
import com.urobot.droid.data.NetModel.Request.RequestSignInSocial
import com.urobot.droid.data.NetModel.Request.RequestSignUp
import com.urobot.droid.data.NetModel.Request.RequestUpdateUser
import com.urobot.droid.data.NetModel.Response.GetPromoModel
import com.urobot.droid.data.NetModel.Response.GetUserResponseModel
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.Headers


interface ApiService {

    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @POST("auth/login/")
    fun login(
            @Body login: RequestLogin
    ): Observable<ResponseLoginModel>

    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @POST("auth/registration/")
    fun signUp(
            @Body login: RequestSignUp
    ): Observable<ResponseLoginModel>

    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @POST("auth/social/")
    fun signinSocial(
            @Body login: RequestSignInSocial
    ): Observable<ResponseLoginModel>

    @Multipart
    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @POST("users/update/")
    fun updateUser(
            @Part("user_id") userId: String,
            @Part("first_name") userName: String,
            @Part("last_name") userLastName: String,
            @Part("phone") phone: String,
            @Part file: MultipartBody.Part

    ): Observable<ResponseLoginModel>


    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @GET("users/{uuid}")
    fun getUserById(
            @Path("uuid") login: String
    ): Observable<GetUserResponseModel>

    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @POST("auth/reset-password/")
    fun forgotPass(
            @Query("email") email: String
    ): Observable<ResponseBody>

    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @POST("promo-codes/create")
    fun getRefCode(
            @Path("Authorization") token: String
    ): Observable<GetPromoModel>
}