package com.urobot.droid.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.data.NetModel.Request.RequestUpdateUser
import com.urobot.droid.db.User
import com.urobot.droid.db.UserDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.io.File

class UserRepository(private val userDao: UserDao) {

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

    fun update(user: User, fileUri: File) {
        try {
            var requestFile: RequestBody
            requestFile = RequestBody.create(
                    MediaType.parse("image.jpg"),
                    fileUri
            )
            val partBody =
                    MultipartBody.Part.createFormData("photo", fileUri.getName(), requestFile)

            val apiService: ApiService = Apifactory.create()
            val loginBody = RequestUpdateUser(user.id, "", user.cellPhone, user.name, "")
            apiService.updateUser(user.id, user.name!!, "", user.cellPhone!!, partBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->

                    }, { error ->
                        Log.d("Result", "Error ")
                        error.printStackTrace()
                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }
}