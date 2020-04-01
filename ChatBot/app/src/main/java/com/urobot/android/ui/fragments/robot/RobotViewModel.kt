package com.urobot.android.ui.fragments.robot

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.android.Apifactory
import com.urobot.android.Helper.Utils.isNetworkConected
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.SharedManager
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import com.urobot.android.ui.fragments.chats.ChatsViewModel
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
