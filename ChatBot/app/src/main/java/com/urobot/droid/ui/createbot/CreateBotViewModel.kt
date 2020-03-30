package com.urobot.droid.ui.createbot

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: ChatsViewModel.IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>
    var resultBotId: Int = -1
    lateinit var list: List<List<GetAllScriptsModel>>


    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    val getAllScriptsLivaData: MutableLiveData<List<List<GetAllScriptsModel>>> = MutableLiveData()
    val getAllServicesLivaData: MutableLiveData<List<GetAllServicesModel>> = MutableLiveData()


    fun deleteBotContentAndScripts(
        token:
        String, botContentItem: BotContentItem
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            //            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()

            val apiService: ApiService = Apifactory.create()
            val response = apiService.deleteScript(token, botContentItem.id!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getAllContentAndScripts(token)
                }
            }
        }

    }

    fun createBotContentAndScripts(token:
                                   String, botContentItem: BotContentItem) {

        CoroutineScope(Dispatchers.IO).launch {

            //            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()

            val apiService: ApiService = Apifactory.create()
            val listscripts: ArrayList<UpdateOrCreateScriptsModel> = ArrayList()


            !
            listscripts.add(
                UpdateOrCreateScriptsModel(
                    botContentItem.description,
                    0,
                    "text",
                    botContentItem.parent_id,
                    botContentItem.level,
                    botContentItem.id,
                    botContentItem.action,
                    botContentItem.isEmpty,
                    botContentItem.list_buttons
                )
            )

            for (buttons in botContentItem.list_buttons!!) {
                var contain = false
                if (list != null) {
                    for (level in list) {
                        val listLevelContent: ArrayList<BotContentItem> = ArrayList()
                        for (item in level) {
                            if (item.uid == botContentItem.parent_id!! + buttons.id!!) {
                                contain = true
                            }
                        }
                    }
                }
                val list: ArrayList<ServiceButtons>? = ArrayList()

                if (!contain) {
                    listscripts.add(
                        UpdateOrCreateScriptsModel(
                            "",
                            0,
                            "text",
                            botContentItem.id,
                            botContentItem.level!! + 1,
                            botContentItem.parent_id!! + buttons.id!!,
                            buttons.id,
                            true,
                            list
                        )
                    )
                }
            }

            val modelList = listscripts.toList()

            val requestMessage = RequestBotScripts(
                resultBotId,
                modelList
            )
            apiService.createScripts(token, requestMessage)


            withContext(Dispatchers.Main) {

            }

        }
    }

    fun setBotId(id: Int) {
        resultBotId = id
    }

    fun getAllContentAndScripts(token:String){
        CoroutineScope(Dispatchers.IO).launch {
            //            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()
            val apiService: ApiService = Apifactory.create()
            val response = apiService.getAllScripts(token, resultBotId)

            withContext(Dispatchers.Main) {

                if(response.body() != null ){


                    list = response.body() as ArrayList<List<GetAllScriptsModel>>

                    getAllScriptsLivaData.value = list

                }
            }
        }
    }

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}