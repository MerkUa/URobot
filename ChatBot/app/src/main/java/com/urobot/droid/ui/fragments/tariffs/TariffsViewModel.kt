package com.urobot.droid.ui.fragments.tariffs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.model.cmsModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun getAllIndustryFromNet(token: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()
            val response = apiService.getCms(token)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { listener?.onTariffsResult(it) }
                }
            }
        }
    }


    interface ITariffsContract {
        fun onTariffsResult(prmo: List<cmsModel>)
    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}
