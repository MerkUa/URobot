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
import com.urobot.droid.data.model.GetMessageModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun getMessage(token:String, contactId: Int?){
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

    fun sendMessage(token:String, id:Int, message:String){
        CoroutineScope(Dispatchers.IO).launch {
            val apiService: ApiService = Apifactory.create()

            val requestMessage = RequestMessage(
                id,
                message
            )
            apiService.sendMessage(token, requestMessage)
        }
    }

    override fun onUpdateResult(user: User) {

    }

    override fun onUpdateError() {
    }
}
