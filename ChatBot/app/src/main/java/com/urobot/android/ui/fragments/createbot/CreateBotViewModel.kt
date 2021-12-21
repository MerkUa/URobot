package com.urobot.android.ui.fragments.createbot

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.urobot.android.Apifactory
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.model.GetRobotModel
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateBotViewModel(application: Application) : AndroidViewModel(application),
    IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User?> = _currentUser

    val createRobotLiveData: MutableLiveData<GetRobotModel> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        repository = UserRepository(userDao, this)
        viewModelScope.launch(Dispatchers.IO) {
            _currentUser.postValue(repository.getCurrentUser())
        }
    }

    fun createRobot(name: String, description: String, repeat: String) {
        viewModelScope.launch(IO) {
            currentUser.value?.token?.let { token ->

                val apiService: ApiService = Apifactory.create()
                val response = apiService.createRobot(token, name, description, repeat)

                withContext(Main) {
                    if (response.isSuccessful) {
                        createRobotLiveData.value = response.body()
                    } else {
                        Toast.makeText(
                            getApplication(),
                            "Ooops: Something else went wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}