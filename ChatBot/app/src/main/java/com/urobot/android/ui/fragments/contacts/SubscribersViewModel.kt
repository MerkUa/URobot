package com.urobot.android.ui.fragments.contacts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.android.Apifactory
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.model.Contact
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscribersViewModel(application: Application) : AndroidViewModel(application),
    IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: IContactsContract? = null

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

    fun setListener(listener: IContactsContract) {
        this.listener = listener
    }

    fun getSubscribers(token: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()

            apiService.getChats(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val list = arrayListOf<Contact>()
                    for (contact in result) {
                        list.add(
                            Contact(
                                contact.id.toString(), contact.firstName + " " + contact.lastName,
                                "",
                                "contact.",
                                contact.photo!!,
                                contact.messengerId
                            )
                        )
                    }
                    Log.d("Merk ", "Merk " + list.size)
                    listener?.onGetSubscribersResult(list)

                }, { error ->

                })

        }
    }

    interface IContactsContract {
        fun onGetSubscribersResult(list: ArrayList<Contact>)
    }
}

