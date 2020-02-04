package com.urobot.droid.ui.fragments.chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.model.Chat
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatsViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
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


    fun getContactId(token: String){

CoroutineScope(Dispatchers.IO).launch {

    val apiService: ApiService = Apifactory.create()

//    val token = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).userDao().getUser()

    val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()

    if (resultBotId != null) {

            apiService.getContacts(token, resultBotId.botId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val list = arrayListOf<Chat>()
                    for (contact in result) {
                        list.add(
                            Chat(
                                contact.id!!, contact.firstName + " " + contact.lastName,
                                contact.photo!!,
                                true, false, "12:07", " "
                            )
                        )
                        listener?.onGetContactsResult(list)
                    }

                }, { error ->

                })

    }
}






//        resultBotId?.let { apiService.getContacts(User.value!!.token!!, it) }

}

    fun setListener(listener: IChatsContract) {
        this.listener = listener
    }


    interface IChatsContract {
        fun onGetContactsResult(list: ArrayList<Chat>)
    }

    override fun onUpdateResult(user: User) {

    }

    override fun onUpdateError() {
    }
}