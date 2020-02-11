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
        @Part file: MultipartBody.Part?,
        @Part("photo") photo: RequestBody?

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
        @Header("Authorization") token: String
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
        @Query("contact_id")contact_id: String?,
        @Query("page") page : Int?,
        @Query("limit") limit : Int?
    ): Response<GetMessageModel>

    /** Send Message Text */
    @POST("bots/send-message")
    suspend fun sendMessage(
        @Header("Authorization") authorization: String,
        @Body message: RequestMessage
    ): Response<ResponseBody>

    /** Send Message Image */
    @Multipart
    @POST("bots/send-message")
    suspend fun sendImageMessage(
        @Header("Authorization") authorization: String,
        @Query("type") type:String,
        @Query("contact_id") contact_id:String,
        @Part file: MultipartBody.Part?,
        @Part("data") data: RequestBody?
    ): Response<ResponseBody>

    /** bot Scripts */

    @POST("scripts/create")
    suspend fun createScripts(
    @Header("Authorization") authorization: String,
    @Body botScripts : RequestBotScripts
    ): Response<ResponseBody>

    @PUT("scripts/update")
    suspend fun updateScripts(
        @Header("Authorization") authorization: String,
        @Query("bot_id") botId:Int,
        @Query("scripts") scripts: UpdateOrCreateScriptsModel
    ) : Response<ResponseBody>

    @GET("scripts")
    suspend fun getAllScripts(
        @Header("Authorization") authorization: String,
        @Query("robot_id") botId: Int
    ): Response<ArrayList<ArrayList<GetAllScriptsModel>>>

    @GET("users/robots")
    suspend fun getAllRobots(
        @Header("Authorization") authorization: String
    ): Response<ArrayList<GetAllRobotsModel>>

    /** Services */
    //Calendar
    //Create
    @POST("services/create")
    suspend fun createCalendarServices(
        @Header("Authorization") authorization: String,
        @Body botScripts : RequestBotCalendarService
    ) : Response<ResponseBody>
    //Update
    @PUT("services/update")
    suspend fun updateOnlineRecordService(
        @Header("Authorization") authorization: String,
        @Body updateOnlineRecord : UpdateBotCalendarService
    ) : Response<ResponseBody>

    //Payment
    //Create
    @POST("services/create")
    suspend fun createPaymentServices(
        @Header("Authorization") authorization: String,
        @Body botScripts : RequestBotPaymentService
    ) : Response<ResponseBody>
    //Update
    @PUT("services/update")
    suspend fun updatePaymentService(
        @Header("Authorization") authorization: String,
        @Body updatePayment : UpdatePaymentService
    ) : Response<ResponseBody>


    @GET("services")
    suspend fun getAllServices(
        @Header("Authorization") authorization: String,
        @Query("bot_id") botId:Int
    ) : Response<List<GetAllServicesModel>>

    @GET("industry")
    suspend fun getAllIndustry(
        @Header("Authorization") authorization: String) : Response<List<GetAllIndustryModel>>

    @PUT("users/update/industry")
    suspend fun updateIndustry(
        @Header("Authorization") authorization: String,
        @Body ids: ArrayList<IdsModel>
    ) : Response<ResponseBody>


    @PUT("devices/registration-token")
    suspend fun registerDeviceId(
        @Header("Authorization") authorization: String,
        @Query("token") deviceId: String,
        @Query("type") type:String
    ) : Response<ResponseBody>

}