package com.urobot.droid.ui.createbot

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.RequestBotScripts
import com.urobot.droid.data.model.*
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateBotViewModel(application:Application) : AndroidViewModel(application), IUserContract{

    private val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
    private var listener: ChatsViewModel.IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    val getAllScriptsLivaData: MutableLiveData<List<List<GetAllScriptsModel>>> = MutableLiveData()

    fun createBotContentAndScripts(token:
                                   String, botContentItem: BotContentItem) {

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getTelegramBotId()

            val apiService: ApiService = Apifactory.create()

            for (item in botContentItem.list_buttons!!.indices){

                val modelList =  listOf(
                    UpdateOrCreateScriptsModel(listOf(), listOf(Message(
                        botContentItem.description, 0, "", listOf(
                            Button(
                                "",
                                1,
                                botContentItem.list_buttons!![item].id
                            )
                        )
                    )), botContentItem.parent_id, botContentItem.id)
                )

                val requestMessage = RequestBotScripts(
                    resultBotId?.botId!!,
                    modelList
                )
                val response =  apiService.createScripts(token, listOf(requestMessage))
            }

            withContext(Dispatchers.Main) {

            }

        }
    }

    fun getAllContentAndScripts(token:String){

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getTelegramBotId()
            val apiService: ApiService = Apifactory.create()
            val response =  apiService.getAllScripts(token, resultBotId?.botId!!)

            withContext(Dispatchers.Main) {

                if(response.body() != null ){

                    val list: List<List<GetAllScriptsModel>>

                    list = response.body() as ArrayList<List<GetAllScriptsModel>>

                    getAllScriptsLivaData.value = list

                }
            }
        }
    }

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}