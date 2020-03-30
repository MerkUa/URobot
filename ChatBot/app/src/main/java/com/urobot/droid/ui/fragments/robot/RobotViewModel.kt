package com.urobot.droid.ui.fragments.robot

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Helper.Utils.isNetworkConected
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.SharedManager
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RobotViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
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

    fun registerDeviceId(context: Context, token: String) {

        if (isNetworkConected(context)) {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val tokenFb = SharedManager(context).tokenFb
                    val apiService: ApiService = Apifactory.create()
                    val type = "android"
                    apiService.registerDeviceId(token, tokenFb, type)
                } catch (e: Exception) {
                }
            }

        }
    }

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}
