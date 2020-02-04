package com.urobot.droid.ui.fragments.industry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.model.GetAllIndustryModel
import com.urobot.droid.data.model.GetAllServicesModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.ubot.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IndustryViewModel(application: Application) : AndroidViewModel(application), IUserContract  {

    private val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
    private var listener: SettingsViewModel.ISettingsContract? = null

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

    fun setListener(listener: SettingsViewModel.ISettingsContract) {
        this.listener = listener
    }


    private val getAllIndustryLivaData : MutableLiveData<List<GetAllIndustryModel>> = MutableLiveData()
    private val getUserIndustryLivaData : MutableLiveData<List<String>> = MutableLiveData()

    fun getAllIndustry(token:String) {

        CoroutineScope(Dispatchers.IO).launch {

            // read from DB
            // take List from DB
            //

            val apiService: ApiService = Apifactory.create()

            val response = apiService.getAllIndustry(token)

            withContext(Dispatchers.Main) {

                getAllIndustryLivaData.value =  response.body()
            }

        }

    }

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}