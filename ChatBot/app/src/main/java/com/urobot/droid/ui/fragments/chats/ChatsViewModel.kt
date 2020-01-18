package com.urobot.droid.ui.fragments.chats

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatsViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
    private var listener: IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }


    fun getContactId(){

CoroutineScope(Dispatchers.IO).launch {

    val apiService: ApiService = Apifactory.create()

//    val token = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).userDao().getUser()

    val resultBotId = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getTelegramBotId()

    if (resultBotId != null) {
        currentUser.value?.token?.let {
            apiService.getContacts(it, resultBotId.botId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result->
                    result.id
                }
        }


    }
}






//        resultBotId?.let { apiService.getContacts(User.value!!.token!!, it) }

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