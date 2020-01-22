package com.urobot.droid.ui.createbot

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.model.MessageScript
import com.urobot.droid.data.model.UpdateScriptsModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class CreateBotViewModel(application:Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
    private var listener: ChatsViewModel.IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    var createBotLiveData: MutableLiveData<UpdateScriptsModel> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    fun getBotContentAndScripts(token:String) {

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getTelegramBotId()
            val apiService: ApiService = Apifactory.create()


            //MockData
            val modelList =  UpdateScriptsModel(0, listOf(MessageScript(
                0, "", "text"
            )))

            val str = Gson().toJson(modelList)
            val jsonObject = JSONObject(str)

            val response =  apiService.putUpdateScripts(token, resultBotId?.botId!!,jsonObject)

            withContext(Dispatchers.Main) {
                createBotLiveData.value
            }

        }
    }

    override fun onUpdateResult(user: User) {

    }

    override fun onUpdateError() {

    }
}