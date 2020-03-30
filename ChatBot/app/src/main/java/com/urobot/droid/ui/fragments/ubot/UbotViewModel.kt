package com.urobot.droid.ui.fragments.ubot

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Helper.Utils
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.AddBotConfig
import com.urobot.droid.data.NetModel.Request.RequestAddBot
import com.urobot.droid.data.model.GetAllRobotsModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UbotViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: ChatsViewModel.IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>
    var userToken: String = ""

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    val getAllScriptsLivaData: MutableLiveData<List<GetAllRobotsModel>> = MutableLiveData()

    fun getAllContentAndScripts(token: String, context: Context) {
        userToken = token
        if (Utils.isNetworkConected(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d("Merk", "try")

                    //            val resultBotId =
//                UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()
                    val apiService: ApiService = Apifactory.create()
                    val response = apiService.getAllRobots(token)
                    withContext(Dispatchers.Main) {
                        Log.d("Merk", "Main")

                        if (response.body() != null) {
                            val list: List<GetAllRobotsModel>

                            list = response.body() as ArrayList<GetAllRobotsModel>

                            getAllScriptsLivaData.value = list

                        } else {
                            Toast.makeText(
                                getApplication(),
                                "Ooops: Something else went wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
    fun addBot(robotId: String, messengerId: Int, token: String, code: String, context: Context) {
        if (Utils.isNetworkConected(context)) {
//            getAllContentAndScripts(userToken, context)
            CoroutineScope(Dispatchers.IO).launch {

                val apiService: ApiService = Apifactory.create()

                val config = AddBotConfig(
                    token,
                    code
                )
                val requstAddBot = RequestAddBot(
                    robotId, messengerId, config
                )

                val response = apiService.addBots(userToken, requstAddBot)

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful) {
                        getAllContentAndScripts(userToken, context)
                    }
                }
            }
        }
    }

    fun update(context: Context) {
        getAllContentAndScripts(userToken, context)
    }

    fun editBot(robotId: String, title: String, description: String, context: Context) {
        if (Utils.isNetworkConected(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                val apiService: ApiService = Apifactory.create()
                val response = apiService.updateRobot(userToken, robotId, title, description)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getAllContentAndScripts(userToken, context)
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

    fun deleteBot(robotId: String, context: Context) {
        if (Utils.isNetworkConected(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                val apiService: ApiService = Apifactory.create()
                val response = apiService.deleteRobot(userToken, robotId)
//
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getAllContentAndScripts(userToken, context)
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
}