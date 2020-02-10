package com.urobot.droid.ui.fragments.ubotservice

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.data.NetModel.Request.RequestBotCalendarService
import com.urobot.droid.data.NetModel.Request.RequestBotPaymentService
import com.urobot.droid.data.model.*
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import com.urobot.droid.ui.fragments.chats.ChatsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServicesViewModel(application:Application)  : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
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

            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()
            val apiService: ApiService = Apifactory.create()
            if (resultBotId != null) {
                val response = apiService.getAllServices(token, resultBotId?.botId!!)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        getAllServicesLivaData.value = response.body()

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

    fun createOnlineRecordService(nameCalendar:String, token: String, dataListModel : OnlineRecordModel?, type_id : Int){

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()
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

                if(response.isSuccessful){

                } else{
                    Toast.makeText(getApplication(), "Ooops: Something else went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun updateCalendarServices(nameCalendar:String, token: String, dataListModel : OnlineRecordModel?, serviceId : Int){

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()

            val apiService: ApiService = Apifactory.create()

            val model = UpdateBotCalendarService(
                serviceId,
                resultBotId?.botId!!,
                nameCalendar,
                "description",
                dataListModel
            )

            val response = apiService.updateOnlineRecordService(token,  model)

            withContext(Dispatchers.Main){

                if(response.isSuccessful){

                } else{
                    Toast.makeText(getApplication(), "Ooops: Something else went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun createPaymentService(namePaymentServices:String ,token: String, dataListModel : PaymentModel?, type_id : Int){

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()
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

                if(response.isSuccessful){

                } else{
                    Toast.makeText(getApplication(), "Ooops: Something else went wrong", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    fun updatePaymentServices(namePayment:String, token: String, dataListModel : PaymentModel?, serviceId : Int ){

        CoroutineScope(Dispatchers.IO).launch {

            val resultBotId = UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()

            val model = UpdatePaymentService(
                serviceId,
                resultBotId?.botId!!,
                namePayment,
                "description",
                dataListModel
            )

            val apiService: ApiService = Apifactory.create()
            val response = apiService.updatePaymentService(token, model)

            withContext(Dispatchers.Main){

                if(response.isSuccessful){

                } else{
                    Toast.makeText(getApplication(), "Ooops: Something else went wrong", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}

