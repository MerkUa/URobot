package com.urobot.droid

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.urobot.droid.data.SharedManager


class ChatBotApplication : Application() {

    /* Firebase Instance */
//    private var firebaseInstanceId: FirebaseInstanceId? = null

    override fun onCreate() {
        super.onCreate()
        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG)) //enable logging when app is in debug mode
                .twitterAuthConfig(
                        TwitterAuthConfig(
                                resources.getString(com.urobot.droid.R.string.com_twitter_sdk_android_CONSUMER_KEY),
                                resources.getString(com.urobot.droid.R.string.com_twitter_sdk_android_CONSUMER_SECRET)
                        )
                ) //pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true) //enable debug mode
                .build()
        //finally initialize twitter with created configs
        Twitter.initialize(config)
        Stetho.initializeWithDefaults(this)
//        startKoin {
//            androidContext(this@ChatBotApplication)
//            modules(appModule)
//        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                SharedManager(baseContext).tokenFb = token!!
            })
    }
}