package com.urobot.droid.service

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFireBaseMessagingService : FirebaseMessagingService() {

    companion object{
        const val TAG ="FB"
    }

    override fun onCreate() {

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: " + remoteMessage.from)

        val data = remoteMessage.data
        val result = data["chat_id"]

        Intent().also { intent ->
            intent.action = "com.example.broadcast.MY_NOTIFICATION"
            intent.putExtra("data", result)
            sendBroadcast(intent)
            Log.d("ROCK", result.toString())
        }


    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        sendRegistrationToServer(p0)
    }

    private fun sendRegistrationToServer(p0: String) {

    }

    private fun handleNow() {

    }

    private fun scheduleJob() {

    }



}