package com.urobot.android.ui.dialogs

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.urobot.android.Apifactory
import com.urobot.android.Helper.Utils
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.data.NetModel.Request.Type
import com.urobot.android.data.model.GetAllServicesModel
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreateEventDialogViewModel(application: Application) : AndroidViewModel(application),
    IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: IEventContract? = null

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: UserRepository
    // LiveData gives us updated words when they change.
    val currentUser: LiveData<User>
    lateinit var contextApp: Context
    lateinit var tokenUser: String


    val getAllServicesLivaData: MutableLiveData<List<GetAllServicesModel>> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        repository = UserRepository(userDao, this)
        currentUser = repository.user

    }

    fun getAllServices(token: String, context: Context) {
        contextApp = context
        tokenUser = token
        if (Utils.isNetworkConected(context)) {
            CoroutineScope(Dispatchers.IO).launch {

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
            }
        }
    }

    interface IEventContract {
        fun onSendPhotoResult(data: String)
    }

    fun setListener(listener: IEventContract) {
        this.listener = listener
    }


    override fun onUpdateResult(user: User) {}
    override fun onUpdateError() {}
    fun sendImage(file: File, id: Long, robotId: String) {
        if (Utils.isNetworkConected(contextApp)) {
            val requestBody: RequestBody =
                RequestBody.create("image/*".toMediaTypeOrNull(), file)

            val fileUpload =
                MultipartBody.Part.createFormData("data", file.name, requestBody)

            CoroutineScope(Dispatchers.IO).launch {
                val apiService: ApiService = Apifactory.create()
                var response = apiService.sendImageToEvent(
                    tokenUser, Type.Image.type,
                    id, robotId, fileUpload, requestBody
                )
                if (response.isSuccessful) {
                    response.body()?.data?.let { listener?.onSendPhotoResult(it) }

                }
            }
        }
    }
}




