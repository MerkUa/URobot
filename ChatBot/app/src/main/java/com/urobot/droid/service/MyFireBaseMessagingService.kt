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

//        val intent = Intent("ActionChatId")
//        intent.action = "ActionChatId"
//        intent.putExtra("chat_id", result)
//        Log.d("intent", intent.toString())
//        Log.d("intent", result.toString())
//        this.sendBroadcast(intent.setAction("chat_id"))
//        broadcaster!!.sendBroadcast(intent)

//        val myIntent = Intent("ActionChatId")
//        myIntent.putExtra("action", result)
//        Log.d("ROCK", myIntent.toString())
//        this.sendBroadcast(myIntent)

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