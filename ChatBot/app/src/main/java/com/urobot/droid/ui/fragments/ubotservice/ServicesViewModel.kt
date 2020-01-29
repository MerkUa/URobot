package com.urobot.droid.ui.fragments.ubotservice

import android.app.Application
import androidx.lifecycle.*
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.RequestBotCalendarService
import com.urobot.droid.data.NetModel.Request.RequestBotPaymentService
import com.urobot.droid.data.model.GetAllServicesModel
import com.urobot.droid.data.model.OnlineRecordModel
import com.urobot.droid.data.model.PaymentModel
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServicesViewModel(application:Application)  : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
    private var listener: ChatsViewModel.IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    val getAllServicesLivaData : MutableLiveData<List<GetAllServicesModel>> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        repository = UserRepository(userDao, this)
        currentUser = repository.User

    }

    fun getAllServices(token:String){

        CoroutineScope(Dispatchers.IO).launch {
            val resultBotId = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getTelegramBotId()
            val apiService: ApiService = Apifactory.create()

            val response =  apiService.getAllServices(token,resultBotId?.botId!!)

            withContext(Dispatchers.Main){

               getAllServicesLivaData.value = response.body()

            }
        }
    }

    fun createOnlineRecordService(nameCalendar:String, token: String, dataListModel : List<OnlineRecordModel?>, type_id : Int){

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getTelegramBotId()
            val requestServices =
                RequestBotCalendarService(
                    resultBotId?.botId!!,
                    nameCalendar,
                    "description",
                    type_id,
                    dataListModel
                )


            val apiService: ApiService = Apifactory.create()
            val response = requestServices.let { apiService.createCalendarServices(token, it) }

            withContext(Dispatchers.Main){

            }
        }
    }

    fun createPaymentService(namePaymentServices:String ,token: String, dataListModel : List<PaymentModel?>, type_id : Int){

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication(), CoroutineScope(Dispatchers.IO)).botDao().getTelegramBotId()
            val requestServices = RequestBotPaymentService(
                resultBotId?.botId!!,
                namePaymentServices,
                "description",
                type_id,
                dataListModel
            )

            val apiService: ApiService = Apifactory.create()
            val response = requestServices.let { apiService.createPaymentServices(token, it) }

            withContext(Dispatchers.Main){

            }
        }
    }


    fun updateServices(){

    }
    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}

