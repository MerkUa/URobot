package com.urobot.droid.data

import android.content.Context
import android.content.SharedPreferences

class SharedManager(val context: Context) {

    private  val PREFERENCES_NAME = "ChatBot"
    private  val USER_TOKEN = "TokenFb"
    private val PAYMENTISBUY = "PaymentIsBuy"
    private val CRMISBUY = "CRMIsBuy"
    private  val DEFAULT = "DEFAULT"


    private fun get(): SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var tokenFb: String
        get() = get().getString(USER_TOKEN, DEFAULT)!!
        set(value) = get().edit()
            .putString(USER_TOKEN, value)
            .apply()


    var paymentIsBuy: Boolean
        get() = get().getBoolean(PAYMENTISBUY, false)
        set(value) = get().edit()
            .putBoolean(PAYMENTISBUY, value)
            .apply()

    var crmIsBuy: Boolean
        get() = get().getBoolean(PAYMENTISBUY, false)
        set(value) = get().edit()
            .putBoolean(PAYMENTISBUY, value)
            .apply()

}