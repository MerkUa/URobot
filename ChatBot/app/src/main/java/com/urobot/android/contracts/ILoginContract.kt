package com.urobot.android.contracts

import com.urobot.android.NetModel.Industries
import com.urobot.android.db.User

interface ILoginContract {

    fun onLoginResult(user: User)
    fun onLoginError()
    fun onSignUpResult()
    fun onForgotPassError()
    fun onForgotPassSuccess()
    fun insertIndustryDB(result: List<Industries>?)


}