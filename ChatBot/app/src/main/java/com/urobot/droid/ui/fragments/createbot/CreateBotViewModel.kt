package com.urobot.droid.ui.fragments.createbot

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.model.GetRobotModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
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
    val currentUser: LiveData<User>

    val createRobotLiveData: MutableLiveData<GetRobotModel> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct

        repository = UserRepository(userDao, this)
        currentUser = repository.User
    }

    fun createRobot(token: String, name: String, description: String) {

        viewModelScope.launch(IO) {
            val apiService: ApiService = Apifactory.create()
            val response = apiService.createRobot(token, name, description)

            withContext(Main) {
                if(response.isSuccessful){
                    createRobotLiveData.value = response.body()
                }else{
                    Toast.makeText(
                        getApplication(),
                        "Ooops: Something else went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}