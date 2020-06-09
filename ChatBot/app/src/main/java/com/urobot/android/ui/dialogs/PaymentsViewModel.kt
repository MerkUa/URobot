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
import com.urobot.android.data.NetModel.Request.RequestBotPaymentService
import com.urobot.android.data.model.PaymentModel
import com.urobot.android.data.model.UpdatePaymentService
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentsViewModel(application: Application) : AndroidViewModel(application), IUserContract {
    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: IPaymentsContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository

    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>
    lateinit var contextApp: Context

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        repository = UserRepository(userDao, this)
        currentUser = repository.User

    }

    fun setListener(listener: IPaymentsContract) {
        this.listener = listener
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
                    listener?.onPaymentsResult()
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
        description: String,
        token: String,
        dataListModel: PaymentModel?,
        serviceId: Int,
        type_id: Int
    ) {

        CoroutineScope(Dispatchers.IO).launch {


            val model = UpdatePaymentService(
                serviceId,
                namePayment,
                description,
                dataListModel,
                type_id
            )

            val apiService: ApiService = Apifactory.create()
            val response = apiService.updatePaymentService(token, model)

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    listener?.onPaymentsResult()
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


    fun deletePaymentServices(
        token: String,
        serviceId: Int
    ) {

        CoroutineScope(Dispatchers.IO).launch {

            val apiService: ApiService = Apifactory.create()
            val response = apiService.deleteService(token, serviceId.toString())

            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    listener?.onPaymentsResult()
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

    interface IPaymentsContract {
        fun onPaymentsResult()
    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}
