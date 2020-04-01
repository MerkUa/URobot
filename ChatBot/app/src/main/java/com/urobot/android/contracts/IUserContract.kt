package com.urobot.android.contracts

import com.urobot.android.db.User

interface IUserContract {

    fun onUpdateResult(user: User)
    fun onUpdateError()



}