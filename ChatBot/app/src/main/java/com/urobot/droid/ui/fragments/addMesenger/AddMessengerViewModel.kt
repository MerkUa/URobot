package com.urobot.droid.ui.fragments.addMesenger

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.RequestCreateBot
import com.urobot.droid.data.SharedManager
import com.urobot.droid.db.BotInfo
import com.urobot.droid.db.Messenger
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.addMesenger.AddmessengerByTypeFragment.Companion.FACEBOOK
import com.urobot.droid.ui.fragments.addMesenger.AddmessengerByTypeFragment.Companion.INSTAGRAM
import com.urobot.droid.ui.fragments.addMesenger.AddmessengerByTypeFragment.Companion.TELEGTAM
import com.urobot.droid.ui.fragments.addMesenger.AddmessengerByTypeFragment.Companion.VIBER
import com.urobot.droid.ui.fragments.addMesenger.AddmessengerByTypeFragment.Companion.VK
import com.urobot.droid.ui.fragments.addMesenger.AddmessengerByTypeFragment.Companion.WHATSAP
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMessengerViewModel(application: Application) : AndroidViewModel(application),
    IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    @SuppressLint("CheckResult")
    fun sendToken(messengerToken: String, code: String, messenger: Messenger) {
        val apiService: ApiService = Apifactory.create()
        val request = RequestCreateBot(
            messengerToken,
            code,
            messenger.messengerId
        )
        Log.d("Result", "token " + currentUser.value!!.token!!)

        Log.d("Result", "chatId " + messenger.messengerId)
        Log.d("Result", "messengerToken " + messengerToken)


        apiService.createBot(currentUser.value!!.token!!, request)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                CoroutineScope(Dispatchers.IO).launch {

                    var messenger = Messenger.Telegram
                    when (messenger.toString()) {
                        TELEGTAM -> {
                            messenger = Messenger.Telegram

                        }
                        VIBER -> {
                            messenger = Messenger.Viber
                            SharedManager(getApplication()!!).viberIsConnected = true
                        }
                        FACEBOOK -> {
                            messenger = Messenger.Facebook
                            SharedManager(getApplication()!!).facebookIsConnected = true
                        }
                        VK -> {
                            messenger = Messenger.Vk
                            SharedManager(getApplication()!!).vkIsConnected = true
                        }
                        WHATSAP -> {
                            messenger = Messenger.WhatsApp
                            SharedManager(getApplication()!!).paymentIsBuy = true
                        }
                        INSTAGRAM -> {
                            messenger = Messenger.Instagram
                            SharedManager(getApplication()!!).paymentIsBuy = true
                        }
                    }

                    val dataBaseBotResult = UserRoomDatabase.getDatabase(getApplication()).botDao()
                        .getById(result.bot_id)

                    if (dataBaseBotResult?.botId == result.bot_id) {

                        UserRoomDatabase.getDatabase(getApplication()).botDao().updateBot(
                            BotInfo(
                                result.bot_id, result.bot_id, messenger.messengerId,
                                messenger.toString()
                            )
                        )

                    } else {

                        UserRoomDatabase.getDatabase(getApplication()).botDao().insertBot(
                            BotInfo(
                                result.bot_id, result.bot_id, messenger.messengerId,
                                messenger.toString()
                            )
                        )
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
