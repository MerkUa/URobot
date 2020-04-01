package com.urobot.android.ui.fragments.industry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urobot.android.Apifactory
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.model.GetAllIndustryModel
import com.urobot.android.data.model.IdsModel
import com.urobot.android.db.Industry
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import com.urobot.android.ui.fragments.ubot.SettingsViewModel
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