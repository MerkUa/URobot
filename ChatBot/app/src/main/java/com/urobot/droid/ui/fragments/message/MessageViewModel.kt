package com.urobot.droid.ui.fragments.message

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.RequestMessage
import com.urobot.droid.data.NetModel.Request.Type
import com.urobot.droid.data.model.GetMessageModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
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
    private var listener: ChatsViewModel.IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    var messageLiveData: MutableLiveData<GetMessageModel> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    fun getMessage(token:String, contactId: String?){

        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()
            val response =  apiService.getMessage(token, contactId, 1,50)

                withContext(Dispatchers.Main) {

                    if(response.isSuccessful){
                        messageLiveData.value = response.body()
                    } else{
                        Toast.makeText(getApplication(), "Ooops: Something else went wrong", Toast.LENGTH_SHORT).show()
                    }

                }

            }
    }

    fun sendTextMessage(token:String, id:Int, message:String){
        CoroutineScope(Dispatchers.IO).launch {
            val apiService: ApiService = Apifactory.create()

            val requestMessage = RequestMessage(
                id,
                message,
                Type.Text.type
            )
            apiService.sendMessage(token, requestMessage)
        }
    }

    fun sendImageMessage(token:String, id:Int, file: File){
        CoroutineScope(Dispatchers.IO).launch {

            val requestBody: RequestBody =
                RequestBody.create(MediaType.parse("image/*"), file)

            val fileUpload =
                MultipartBody.Part.createFormData("data", file.name, requestBody)

            val apiService: ApiService = Apifactory.create()
            apiService.sendImageMessage(token,Type.Image.type,
                id.toString(), fileUpload, requestBody)
        }
    }

    override fun onUpdateResult(user: User) {

    }

    override fun onUpdateError() {
    }
}
