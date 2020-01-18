package com.urobot.droid.data

import android.util.Log
import com.urobot.droid.Apifactory
import com.urobot.droid.NetModel.ResponseLoginModel
import com.urobot.droid.Network.ApiService
import com.urobot.droid.contracts.ILoginContract
import com.urobot.droid.data.NetModel.Request.RequestLogin
import com.urobot.droid.data.NetModel.Request.RequestSignInSocial
import com.urobot.droid.data.NetModel.Request.RequestSignUp
import com.urobot.droid.data.model.LoggedInUser
import com.urobot.droid.db.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val loginContract: ILoginContract) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }

    fun login(username: String, password: String) {
        Log.d("Result", "login ")
        try {
            val apiService: ApiService = Apifactory.create()
            val loginBody = RequestLogin(
                    username,
                    password
            )
            apiService.login(loginBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->

                        var user = User(
                            result.userId!!,  result.firstName, result.lastName!!, result.token!!,
                                result.phone!!, result.photo)
                        loginContract.onLoginResult(user)


                    }, { error ->
                        Log.d("Result", "Error!! " + error.localizedMessage)
                        loginContract.onLoginError()
                        error.printStackTrace()
                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }


    fun signInSocial(type: String, token: String, secret: String?) {
        Log.d("Result", "login ")
        try {
            val apiService: ApiService = Apifactory.create()
            val social = RequestSignInSocial(
                    type,
                    token,
                    secret
            )
            apiService.signinSocial(social)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->

                        var user = User(
                            result.userId!!, result.firstName, result.lastName!!, result.token!!,
                                result.phone!!, result.photo)
                        loginContract.onLoginResult(user)


                    }, { error ->
                        Log.d("Result", "Error!! " + error.localizedMessage)
                        loginContract.onLoginError()
                        error.printStackTrace()
                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }

    fun forgotPass(email: String) {
        try {
            val apiService: ApiService = Apifactory.create()
            apiService.forgotPass(email)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        loginContract.onForgotPassSuccess()
//
//
                    }, { error ->
                        loginContract.onForgotPassError()
                        Log.d("Result", "Error!! " + error.localizedMessage)

                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }


    fun signUp(email: String, password: String, name: String, lastname: String) {
        Log.d("Result", "login ")
        try {
            val apiService: ApiService = Apifactory.create()
            val signUpBody = RequestSignUp(
                    email,
                    password,
                    name,
                    lastname
            )
            apiService.signUp(signUpBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        loginContract.onSignUpResult()

                    }, { error ->
                        Log.d("Result", "Error!! " + error.localizedMessage)
                        loginContract.onLoginError()
                        error.printStackTrace()
                    })

        } catch (e: Throwable) {
            Log.d("Result", "Error " + e.localizedMessage)
        }
    }

    fun getUser(loginResult: ResponseLoginModel) {
        val apiService: ApiService = Apifactory.create()
        apiService.getUserById(loginResult.userId.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    //                Log.d("Result", "TuserId "+ result.userId)
//                Log.d("Result", "username "+ result.username)
//                Log.d("Result", "token "+ result.token)
//                var user = User(loginResult.userId!!,loginResult.username!!,loginResult.token!!,
//                    result.cellPhone!!,result.photoUrl )

//                loginContract.onLoginResult(user)


                }, { error ->
                    Log.d("Result", "Error!! " + error.localizedMessage)
                    loginContract.onLoginError()
                    error.printStackTrace()
                })
    }
}
