package com.urobot.android.ui.fragments.ubot

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import kotlinx.coroutines.launch
import java.io.File

class SettingsViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: ISettingsContract? = null

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

    fun setListener(listener: ISettingsContract) {
        this.listener = listener
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun update(user: User) = viewModelScope.launch {
        repository.update(user)
    }

    fun sendUpdate(user: User, fileUri: File) {
        repository.update(user, fileUri)
    }

    fun sendUpdatePhone(user: User){
        repository.updatePhone(user)
    }

    fun logout(token: String) {

        viewModelScope.launch {
            repository.logout(token)
            userDao.deleteAll()
            listener?.onLogoutResult()
        }
    }


    interface ISettingsContract {
        fun onLogoutResult()
    }

    override fun onUpdateResult(user: User) {
        viewModelScope.launch {

            userDao.update(user)
        }
    }

    override fun onUpdateError() {
    }
}