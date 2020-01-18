package com.urobot.droid.ui.fragments.addMesenger

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.RequestCreateBot
import com.urobot.droid.db.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMessengerViewModel(application: Application) : AndroidViewModel(application),
    IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    fun sendToken(messengerToken: String, chatId: String) {
        val apiService: ApiService = Apifactory.create()
        val request = RequestCreateBot(
            messengerToken,
            chatId
        )
        Log.d("Result", "token " + currentUser.value!!.token!!)

        Log.d("Result", "chatId " + chatId)
        Log.d("Result", "messengerToken " + messengerToken)


        apiService.createBot(currentUser.value!!.token!!, request)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                CoroutineScope(Dispatchers.IO).launch {

      val dataBaseBotResult =  UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getById(result.bot_id)

      if( dataBaseBotResult?.botId == result.bot_id){

      UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().updateBot(
          BotInfo(1, result.bot_id, Messenger.Telegram.messengerId,
              Messenger.Telegram.toString()))

         } else {

          UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().insertBot(
              BotInfo(1, result.bot_id, Messenger.Telegram.messengerId,
                  Messenger.Telegram.toString()))
      }
                }

                Log.d("Result", result.bot_id.toString())

            }, { error ->
                Log.d("Result", "Error ")
                error.printStackTrace()
            })

    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}
