package com.urobot.droid.contracts

import com.urobot.droid.db.User

interface IUserContract {

    fun onUpdateResult(user: User)
    fun onUpdateError()



}