package com.urobot.droid.ui.fragments.industry

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.model.GetAllIndustryModel
import com.urobot.droid.data.model.IdsModel
import com.urobot.droid.db.Industry
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.ubot.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IndustryViewModel(application: Application) : AndroidViewModel(application), IUserContract  {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
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

     val getAllIndustryFromNetLivaData : MutableLiveData<List<GetAllIndustryModel>> = MutableLiveData()
     val getUserIndustryFromLocalDbLivaData : MutableLiveData<List<Industry>> = MutableLiveData()
     var boolLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getAllIndustryFromNet(token:String) {

        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()
            val response = apiService.getAllIndustry(token)

            withContext(Dispatchers.Main) {
                getAllIndustryFromNetLivaData.value =  response.body()
            }
        }
    }

    fun getAllIndustryFromLocalDB(){

        CoroutineScope(Dispatchers.IO).launch {

           val result = UserRoomDatabase.getDatabase(getApplication()).industryDao().getAllIndustry()

            withContext(Dispatchers.Main){
                getUserIndustryFromLocalDbLivaData.value = result
            }
        }

    }

    fun updateIndustry(token:String, list: ArrayList<IdsModel>){

        CoroutineScope(Dispatchers.IO).launch {

            val result = UserRoomDatabase.getDatabase(getApplication()).industryDao().getAllIndustry()
            UserRoomDatabase.getDatabase(getApplication()).industryDao().delete(result)

            for(item in list){
                UserRoomDatabase.getDatabase(getApplication()).industryDao().insertIndustry(Industry(item.id))
            }

            val apiService: ApiService = Apifactory.create()
            val response =  apiService.updateIndustry(token, list)

            withContext(Dispatchers.Main){
                boolLiveData.value = response.isSuccessful
            }
        }
    }

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}