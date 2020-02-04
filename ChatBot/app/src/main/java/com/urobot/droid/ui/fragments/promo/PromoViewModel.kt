package com.urobot.droid.ui.fragments.promo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.urobot.droid.Apifactory
import com.urobot.droid.Network.ApiService
import com.urobot.droid.Repository.UserRepository
import com.urobot.droid.contracts.IUserContract
import com.urobot.droid.db.User
import com.urobot.droid.db.UserRoomDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PromoViewModel(application: Application) : AndroidViewModel(application), IUserContract {

    private val userDao = UserRoomDatabase.getDatabase(application).userDao()
    private var listener: IPromoContract? = null


    private val repository: UserRepository

    val User: LiveData<User>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        repository = UserRepository(userDao, this)
        User = repository.User
    }

    fun setListener(listener: IPromoContract) {
        this.listener = listener
    }

    fun getPromo(token: String) {
        try {
            val apiService: ApiService = Apifactory.create()
            apiService.getRefCode(token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        listener?.onPromoResult(result.promoCode!!)
                    }, { error ->

                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }

    interface IPromoContract {
        fun onPromoResult(prmo: String)
    }

    override fun onUpdateResult(user: User) {
    }

    override fun onUpdateError() {
    }
}
