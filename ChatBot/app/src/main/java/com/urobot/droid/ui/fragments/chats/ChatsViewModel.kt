package com.urobot.droid.ui.fragments.chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase

class ChatsViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
    private var listener: IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val User: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        repository = UserRepository(userDao, this)
        User = repository.User
    }

    fun setListener(listener: IChatsContract) {
        this.listener = listener
    }


    interface IChatsContract {
        fun onLogoutResult()
    }

    override fun onUpdateResult(user: User) {

    }

    override fun onUpdateError() {
    }
}