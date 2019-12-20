package com.urobot.droid.ui.fragments.promo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PromoViewModel(application: Application) : AndroidViewModel(application) {

//    private  val userDao  = UserRoomDatabase.getDatabase(application, viewModelScope).userDao()
//
//    private val repository: UserRepository
//
//    init {
//        repository = UserRepository(userDao)
//    }

    fun getPromo(token: String) {
        try {
            val apiService: ApiService = Apifactory.create()

            apiService.getRefCode(token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->

                        result.promoCode


                    }, { error ->

                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }
}
