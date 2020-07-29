package com.urobot.android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.facebook.stetho.Stetho
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.urobot.android.data.SharedManager

class ChatBotApplication : Application(), LifecycleObserver {

    /* Firebase Instance */
//    private var firebaseInstanceId: FirebaseInstanceId? = null

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel."
        )
        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG)) //enable logging when app is in debug mode
                .twitterAuthConfig(
                        TwitterAuthConfig(
                            resources.getString(com.urobot.android.R.string.com_twitter_sdk_android_CONSUMER_KEY),
                            resources.getString(com.urobot.android.R.string.com_twitter_sdk_android_CONSUMER_SECRET)
                        )
                ) //pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true) //enable debug mode
                .build()
        //finally initialize twitter with created configs
        Twitter.initialize(config)
        Stetho.initializeWithDefaults(this)

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


    fun isInForeground(): Boolean {
        return ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
    }

    fun createNotificationChannel(
        context: Context,
        importance: Int,
        showBadge: Boolean,
        name: String,
        description: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}