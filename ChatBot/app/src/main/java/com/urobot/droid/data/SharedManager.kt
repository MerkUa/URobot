package com.urobot.droid.data

import android.content.Context
import android.content.SharedPreferences

class SharedManager(val context: Context) {

    private  val PREFERENCES_NAME = "ChatBot"
    private  val USER_TOKEN = "TokenFb"
    private val PAYMENTISBUY = "PaymentIsBuy"
    private val CRMISBUY = "CRMIsBuy"
    private  val DEFAULT = "DEFAULT"
    private val TELEGRAM = "Telegram"
    private val FACEBOOK = "Facebook"
    private val VIBER = "Viber"
    private val VK = "vk"
    private val WHATSUP = "Whatsup"
    private val INSTAGRAM = "Instagram"



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

    var telegramIsConnected: Boolean
        get() = get().getBoolean(TELEGRAM, false)
        set(value) = get().edit()
            .putBoolean(TELEGRAM, value)
            .apply()

    var facebookIsConnected: Boolean
        get() = get().getBoolean(FACEBOOK, false)
        set(value) = get().edit()
            .putBoolean(FACEBOOK, value)
            .apply()
    var viberIsConnected: Boolean
        get() = get().getBoolean(VIBER, false)
        set(value) = get().edit()
            .putBoolean(VIBER, value)
            .apply()
    var vkIsConnected: Boolean
        get() = get().getBoolean(VK, false)
        set(value) = get().edit()
            .putBoolean(VK, value)
            .apply()
    var whatsupIsConnected: Boolean
        get() = get().getBoolean(WHATSUP, false)
        set(value) = get().edit()
            .putBoolean(WHATSUP, value)
            .apply()
    var instagramIsConnected: Boolean
        get() = get().getBoolean(INSTAGRAM, false)
        set(value) = get().edit()
            .putBoolean(INSTAGRAM, value)
            .apply()

}