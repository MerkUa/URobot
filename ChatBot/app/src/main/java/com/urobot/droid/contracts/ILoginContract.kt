package com.urobot.droid.contracts

import com.urobot.droid.db.User

interface ILoginContract {

    fun onLoginResult(user: User)
    fun onLoginError()
    fun onSignUpResult()
    fun onForgotPassError()
    fun onForgotPassSuccess()


}