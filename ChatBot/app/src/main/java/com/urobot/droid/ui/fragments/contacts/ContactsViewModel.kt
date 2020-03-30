package com.urobot.droid.ui.fragments.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.model.Contact
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(application: Application) : AndroidViewModel(application), IUserContract {

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


    fun getContacts(token: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()

            apiService.getContacts(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    val list = arrayListOf<Contact>()
                    for (contact in result) {
                        list.add(
                            Contact(
                                contact.id.toString(), contact.firstName + " " + contact.lastName,
                                "",
                                "contact.", contact.photo!!, contact.messengerId
                            )
                        )
                    }
                    listener?.onGetContactsResult(list)

                }, { error ->

                })

        }
    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }

    fun setListener(listener: IContactsContract) {
        this.listener = listener
    }

    interface IContactsContract {
        fun onGetContactsResult(list: ArrayList<Contact>)
    }
}

