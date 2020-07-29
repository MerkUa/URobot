package com.urobot.android.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.urobot.android.Apifactory
import com.urobot.android.Network.ApiService
import com.urobot.android.contracts.IUserContract
import com.urobot.android.db.User
import com.urobot.android.db.UserDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class UserRepository(private val userDao: UserDao, val userContract: IUserContract) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val User: LiveData<User> = userDao.getUser()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    fun getById(id: String): User {
        return userDao.getUserById(id)!!
    }

    fun update(user: User, file: File) {
        try {
            val requestBody: RequestBody =
                RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val fileupload =
                MultipartBody.Part.createFormData("photo", file.name, requestBody)

            val apiService: ApiService = Apifactory.create()
            val description: RequestBody =
                toRequestBody(user.fName!!)
            val place: RequestBody = toRequestBody(user.lName!!)
            val time: RequestBody = toRequestBody(user.cellPhone!!)

            val map: HashMap<String, RequestBody> = HashMap()
            map["first_name"] = description
            map["last_name"] = place
            map["phone"] = time


            apiService.updateUser(user.token!!, map, fileupload, requestBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        var user = User(
                            result.userId!!, result.firstName, result.lastName!!, user.token,
                            result.phone!!, result.photo)
                        userContract.onUpdateResult(user)

                    }, { error ->
                        Log.d("Result", "Error ")
                        error.printStackTrace()
                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }


    fun updatePhone(user: User ){

        val apiService: ApiService = Apifactory.create()
        val description: RequestBody =
            toRequestBody(user.fName!!)
        val place: RequestBody = toRequestBody(user.lName!!)
        val time: RequestBody = toRequestBody(user.cellPhone!!)

        val map: HashMap<String, RequestBody> = HashMap()
        map["first_name"] = description
        map["last_name"] = place
        map["phone"] = time

        apiService.updateUser(user.token!!, map, null, null)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                var user = User(
                    result.userId!!, result.firstName, result.lastName!!, user.token,
                    result.phone!!, result.photo)
                userContract.onUpdateResult(user)

            }, { error ->
                Log.d("Result", "Error ")
                error.printStackTrace()
            })
    }

    fun logout(token: String) {
        try {
            Log.d("Retr", "logout ")

            val apiService: ApiService = Apifactory.create()

            apiService.logout(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.d("Retr", "logout ")

                }, { error ->
                })

        } catch (e: Throwable) {
            Log.d("Retr", "Error " + e.localizedMessage)
        }
    }

    fun toRequestBody(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }
}