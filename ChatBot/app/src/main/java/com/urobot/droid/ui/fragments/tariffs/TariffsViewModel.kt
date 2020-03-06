package com.urobot.droid.ui.fragments.tariffs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase

class TariffsViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: ITariffsContract? = null


    private val repository: UserRepository

    val User: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        repository = UserRepository(userDao, this)
        User = repository.User
    }

    fun setListener(listener: ITariffsContract) {
        this.listener = listener
    }


    interface ITariffsContract {
        fun onTariffsResult(prmo: String)
    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}
