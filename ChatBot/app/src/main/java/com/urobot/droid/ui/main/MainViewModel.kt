package com.urobot.droid.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase

class MainViewModel(application: Application) : AndroidViewModel(application), IUserContract {
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

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}