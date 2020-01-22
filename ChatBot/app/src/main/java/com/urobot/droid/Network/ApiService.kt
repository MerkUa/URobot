package com.urobot.droid.Network

import com.urobot.droid.NetModel.ResponseLoginModel
import com.urobot.droid.data.NetModel.Request.*
import com.urobot.droid.data.NetModel.Response.GetPromoModel
import com.urobot.droid.data.NetModel.Response.GetUserResponseModel
import com.urobot.droid.data.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
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
        @Query("bot_id")botID: Int?
    ): Observable<List<GetContactsModel>>

    @GET("bots/get-messages")
    suspend fun getMessage(
        @Header("Authorization") authorization: String,
        @Query("contact_id")contact_id: Int?,
        @Query("page") page : Int?,
        @Query("limit") limit : Int?
    ): Response<GetMessageModel>

    @POST("bots/send-message")
    suspend fun sendMessage(
        @Header("Authorization") authorization: String,
        @Body message: RequestMessage
    ): Response<ResponseBody>

    @PUT("bots/update-scripts")
    suspend fun putUpdateScripts(
        @Header("Authorization") authorization: String,
        @Query("bot_id") botId:Int,
        @Query("scripts") scripts: JSONObject
    ) : Response<ResponseBody>

    @POST("/services/create")
    suspend fun createServices(
        @Header("Authorization") authorization: String,
        @Query("bot_id") botId: Int,
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("key") key : Int
    ) : Response<CreateOrUpdateServicesModel>

    @PUT("services/update")
    suspend fun updateServices(
        @Header("Authorization") authorization: String,
        @Query("service_id") service_id:Int,
        @Query("bot_id") botId:Int,
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("key") key : Int
    ) : Response<CreateOrUpdateServicesModel>

}