package com.urobot.droid.Network

import com.urobot.droid.NetModel.ResponseLoginModel
import com.urobot.droid.data.NetModel.Request.RequestCreateBot
import com.urobot.droid.data.NetModel.Request.RequestLogin
import com.urobot.droid.data.NetModel.Request.RequestSignInSocial
import com.urobot.droid.data.NetModel.Request.RequestSignUp
import com.urobot.droid.data.NetModel.Response.GetPromoModel
import com.urobot.droid.data.NetModel.Response.GetUserResponseModel
import com.urobot.droid.data.model.CreateWithRobotModel
import com.urobot.droid.data.model.GetContactsModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiService {

    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @POST("auth/login/")
    fun login(
            @Body login: RequestLogin
    ): Observable<ResponseLoginModel>


    @Headers("accept: application/json", "Content-Type: application/json-patch+json")
    @DELETE("auth/logout/")
    fun logout(
        @Query("Authorization") token: String
    ): Observable<ResponseBody>


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
    @POST("users/update/")
    fun updateUser(
        @Header("Authorization") login: String,
        @PartMap phot: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part,
        @Part("photo") photo: RequestBody

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

    @POST("bots/create-with-robot")
    fun createBot(
        @Header("Authorization") authorization: String,
        @Body requestCreateBot: RequestCreateBot
    ): Observable<CreateWithRobotModel>

    @GET("bots/get-contacts")
    fun getContacts(
        @Header("Authorization") authorization: String,
        @Body botID: Int?
    ): Observable<GetContactsModel>
}