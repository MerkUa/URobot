package com.urobot.android.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.android.Apifactory
import com.urobot.android.Helper.Utils.addCalendarEvent
import com.urobot.android.Network.ApiService
import com.urobot.android.R
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.model.CalendarItemModel
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.text.SimpleDateFormat

class MainViewModel(application: Application) : AndroidViewModel(application), IUserContract {
    private val userDao = UserRoomDatabase.getDatabase(application).userDao()

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        repository = UserRepository(userDao, this)
        currentUser = repository.user
    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }

    fun getCalendarEvent(context: Context, token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pattern = "yyyy-MM-dd HH:mm:ss"
                val sdf = SimpleDateFormat(pattern)
                val apiService: ApiService = Apifactory.create()
                val response = apiService.getAllEvents(token)
                if (response.isSuccessful && response.body()!!.isNotEmpty()) {
                    for (item in response.body()!!) {
                        for (details in item.records) {
                            val date = sdf.parse(details.date + " " + details.time)
                            val item = CalendarItemModel(
                                details.id,
                                date.time,
                                context.getString(R.string.booking),
                                item.firstName + " " + item.lastName
                            )
                            addCalendarEvent(context, item)
                        }
//                    var parsedDate = LocalDate.parse("Wednesday, November 21, 2018", DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"))

                    }
                }
            } catch (e: ConnectException) {
            }
        }
    }


}