package com.urobot.droid.contracts

import com.urobot.droid.db.User

interface IUserContract {

    fun onUserUpdateResult(user: User)
    fun onUserUpdateError()
    fun onGetUserResult(user: User)
    fun onGetUserError()


}