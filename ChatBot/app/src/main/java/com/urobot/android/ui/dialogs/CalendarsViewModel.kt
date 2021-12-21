package com.urobot.android.ui.dialogs

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.android.Apifactory
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.NetModel.Request.RequestBotCalendarService
import com.urobot.android.data.model.OnlineRecordModel
import com.urobot.android.data.model.TypeServices
import com.urobot.android.data.model.UpdateBotCalendarService
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalendarsViewModel(application: Application) : AndroidViewModel(application), IUserContract {
    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: ICalendarContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository

    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>
    lateinit var contextApp: Context

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        repository = UserRepository(userDao, this)
        currentUser = repository.user

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
                    nameCalendar,
                    type_id,
                    dataListModel
                )


            val apiService: ApiService = Apifactory.create()
            val response = requestServices.let { apiService.createCalendarServices(token, it) }

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    listener?.onCalendarResult()
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
        descriptionCalendar: String,
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
                descriptionCalendar,
                dataListModel,
                TypeServices.onlineRecord.type_id
            )

            val response = apiService.updateOnlineRecordService(token, model)

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    listener?.onCalendarResult()
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

    fun setListener(listener: ICalendarContract) {
        this.listener = listener
    }

    fun deletePaymentServices(
        token: String,
        serviceId: Int
    ) {

        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()
            val response = apiService.deleteService(token, serviceId.toString())

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    listener?.onCalendarResult()
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

    interface ICalendarContract {
        fun onCalendarResult()
    }


    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}
