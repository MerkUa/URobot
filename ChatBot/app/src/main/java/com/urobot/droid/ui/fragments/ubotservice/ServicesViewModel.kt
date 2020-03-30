package com.urobot.droid.ui.fragments.ubotservice

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urobot.droid.Apifactory
import com.urobot.droid.Helper.Utils
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
import java.net.ConnectException

class ServicesViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: ChatsViewModel.IChatsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>
    lateinit var contextApp: Context

    val getAllServicesLivaData: MutableLiveData<List<GetAllServicesModel>> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        repository = UserRepository(userDao, this)
        currentUser = repository.User

    }

    fun getAllServices(token: String, context: Context) {
        contextApp = context
        if (Utils.isNetworkConected(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiService: ApiService = Apifactory.create()
                    val response = apiService.getAllServices(token)

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
                } catch (e: ConnectException) {
                }
            }
        }
    }

    fun createOnlineRecordService(
        nameCalendar: String,
        token: String,
        dataListModel: OnlineRecordModel?,
        type_id: Int
    ) {

        CoroutineScope(Dispatchers.IO).launch {

            //            val resultBotId =
//                UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()
            val requestServices =
                RequestBotCalendarService(
                    nameCalendar,
                    "description",
                    type_id,
                    dataListModel
                )


            val apiService: ApiService = Apifactory.create()
            val response = requestServices.let { apiService.createCalendarServices(token, it) }

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    getAllServices(token, contextApp)

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

    fun updateCalendarServices(
        nameCalendar: String,
        token: String,
        dataListModel: OnlineRecordModel?,
        serviceId: Int
    ) {

        CoroutineScope(Dispatchers.IO).launch {

            //            val resultBotId =
//                UserRoomDatabase.getDatabase(getApplication()).botDao().getTelegramBotId()

            val apiService: ApiService = Apifactory.create()

            val model = UpdateBotCalendarService(
                serviceId,
                nameCalendar,
                "description",
                dataListModel
            )

            val response = apiService.updateOnlineRecordService(token, model)

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    getAllServices(token, contextApp)
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

    fun createPaymentService(
        namePaymentServices: String,
        descriptionPaymentServices: String,
        token: String,
        dataListModel: PaymentModel?,
        type_id: Int
    ) {

        CoroutineScope(Dispatchers.IO).launch {


            val requestServices = RequestBotPaymentService(
                namePaymentServices,
                descriptionPaymentServices,
                type_id,
                dataListModel
            )

            val apiService: ApiService = Apifactory.create()
            val response = requestServices.let { apiService.createPaymentServices(token, it) }

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    getAllServices(token, contextApp)
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

    fun updatePaymentServices(
        namePayment: String,
        token: String,
        dataListModel: PaymentModel?,
        serviceId: Int
    ) {

        CoroutineScope(Dispatchers.IO).launch {


            val model = UpdatePaymentService(
                serviceId,
                namePayment,
                "",
                dataListModel
            )

            val apiService: ApiService = Apifactory.create()
            val response = apiService.updatePaymentService(token, model)

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {

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

    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
}

