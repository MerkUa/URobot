package com.urobot.android.ui.fragments.chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.android.Apifactory
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.model.Chat
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

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


    fun getContactId(token: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()
            val serverPattern = "yyyy-MM-dd HH:mm:ss"
            val format = SimpleDateFormat("HH:mm")
            val sdf = SimpleDateFormat(serverPattern)
            apiService.getChatsAll(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val list = arrayListOf<Chat>()
                    for (contact in result) {
                        var message = contact.lastMessage?.text ?: ""
                        var time = sdf.parse(contact.lastMessage?.time)
                        list.add(
                            Chat(
                                contact.id!!, contact.firstName + " " + contact.lastName,
                                contact.photo!!,
                                true, false, format.format(time), message, contact.botId!!
                            )
                        )
//                        listener?.onGetContactsResult(list)

                    }
                    listener?.onGetContactsResult(list)

                }, { error ->

                })


        }
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