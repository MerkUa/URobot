package com.urobot.android.ui.fragments.support

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.urobot.android.Apifactory
import com.urobot.android.Network.ApiService
import com.urobot.android.Repository.UserRepository
import com.urobot.android.contracts.IUserContract
import com.urobot.android.db.User
import com.urobot.android.db.UserRoomDatabase
import com.urobot.android.ui.fragments.promo.PromoViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SupportViewModel(application: Application) : AndroidViewModel(application), IUserContract {
    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: PromoViewModel.IPromoContract? = null


    private val repository: UserRepository

    val User: LiveData<User>
    var promo: String = ""

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        repository = UserRepository(userDao, this)
        User = repository.User
        getPromo(User.value?.token.toString())
    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }

    fun getPromo(token: String) {
        try {
            val apiService: ApiService = Apifactory.create()
            apiService.getRefCode(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    promo = result.promoCode.toString()
                }, { error ->

                })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }
}
