package com.urobot.droid.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val User: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(userDao)
        User = repository.User
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }
}