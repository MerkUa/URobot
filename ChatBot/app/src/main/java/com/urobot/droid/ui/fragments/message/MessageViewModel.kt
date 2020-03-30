package com.urobot.droid.ui.fragments.message

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.RequestMessage
import com.urobot.droid.data.NetModel.Request.Type
import com.urobot.droid.data.model.GetMessageModel
import com.urobot.droid.data.model.SendMessageResponseModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MessageViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: IChatMessageContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>
    var recipientId: String = ""
    var userId: String = ""
    var userToken: String = ""
    var page: Int = 1
    var inProcess: Boolean = false

    var messageLiveData: MutableLiveData<GetMessageModel> = MutableLiveData()
    var pushMessageLiveData: MutableLiveData<GetMessageModel> = MutableLiveData()


    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    fun getMessages(token: String, recipient: String, user: String, page: Int) {
        recipientId = recipient
        userId = user
        userToken = token
        CoroutineScope(Dispatchers.IO).launch {
            var response: retrofit2.Response<GetMessageModel>? = null
            val apiService: ApiService = Apifactory.create()
            if (recipient != "-1") {
                response = apiService.getMessage(token, recipient, 1, 50)
            } else {
                response = apiService.getMessageOfContact(token, user, 1, 50)
            }

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        messageLiveData.value = response.body()

                    } else {
                        Toast.makeText(
                            getApplication(),
                            "Ooops: Something else went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    fun getNewMessages() {
        CoroutineScope(Dispatchers.IO).launch {
            var response: retrofit2.Response<GetMessageModel>? = null
            val apiService: ApiService = Apifactory.create()
            if (recipientId != "-1") {
                response = apiService.getMessage(userToken, recipientId, 1, 50)
            } else {
                response = apiService.getMessageOfContact(userToken, userId, 1, 50)
            }
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        pushMessageLiveData.value = response.body()

                    } else {
                        Toast.makeText(
                            getApplication(),
                            "Ooops: Something else went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    fun getOldMessage() {
        if (!inProcess) {
            CoroutineScope(Dispatchers.IO).launch {
                page = page + 1
                inProcess = true
                var response: retrofit2.Response<GetMessageModel>? = null
                val apiService: ApiService = Apifactory.create()
                if (recipientId != "-1") {
                    response = apiService.getMessage(userToken, recipientId, page, 50)
                } else {
                    response = apiService.getMessageOfContact(userToken, userId, page, 50)
                }
                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        messageLiveData.value = response.body()
//                        response.message()
                        inProcess = false

                    } else {
                        Toast.makeText(
                            getApplication(),
                            "Ooops: Something else went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    fun sendTextMessage(token: String, id: Int, message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiService: ApiService = Apifactory.create()

            val requestMessage = RequestMessage(
                id,
                message,
                Type.Text.type
            )
            CoroutineScope(Dispatchers.IO).launch {
                var response: retrofit2.Response<SendMessageResponseModel>? = null
                val apiService: ApiService = Apifactory.create()
                if (recipientId != "-1") {
                    response = apiService.sendMessage(token, requestMessage)
                } else {
                    response = apiService.sendMessageToContact(token, requestMessage)
                }
                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        response.body()?.id?.let { listener?.onMessageSent(it) }
                    }
                }
            }
        }
    }

    fun sendImageMessage(token: String, id: Int, file: File) {
        CoroutineScope(Dispatchers.IO).launch {

            val requestBody: RequestBody =
                RequestBody.create(MediaType.parse("image/*"), file)

            val fileUpload =
                MultipartBody.Part.createFormData("data", file.name, requestBody)

            CoroutineScope(Dispatchers.IO).launch {
                var response: retrofit2.Response<SendMessageResponseModel>? = null
                val apiService: ApiService = Apifactory.create()
                if (recipientId != "-1") {
                    response = apiService.sendImageMessage(
                        token, Type.Image.type,
                        id.toString(), fileUpload, requestBody
                    )
                } else {
                    response = apiService.sendImageMessageToContact(
                        token, Type.Image.type,
                        id.toString(), fileUpload, requestBody
                    )
                }

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        response.body()?.id?.let { listener?.onMessageSent(it) }
                    }
                }
            }
        }
    }

    fun setListener(listener: IChatMessageContract) {
        this.listener = listener
    }

    interface IChatMessageContract {
        fun onMessageSent(messageId: Int)
    }

    override fun onUpdateResult(user: User) {

    }

    override fun onUpdateError() {
    }
}
