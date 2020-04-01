package com.urobot.android.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.urobot.android.ChatBotApplication
import com.urobot.android.R
import com.urobot.android.ui.main.MainChatActivity


class MyFireBaseMessagingService : FirebaseMessagingService() {

    companion object{
        const val TAG ="FB"
    }

    override fun onCreate() {

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("MERK", "From: " + remoteMessage.from)

        val data = remoteMessage.data
        val result = data["chat_id"]

        Intent().also { intent ->
            intent.action = "com.example.broadcast.MY_NOTIFICATION"
            intent.putExtra("data", result)
            sendBroadcast(intent)
            Log.d("ROCK", result.toString())
        }



        if (!(applicationContext as ChatBotApplication).isInForeground()) {
            sendNotification(data, result)
        }

    }

    private fun sendNotification(messageBody: MutableMap<String, String>, result: String?) {
        val intent = Intent(this, MainChatActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("message", result)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.app_name))
            .setSmallIcon(R.drawable.chat_selector)
            .setContentTitle(messageBody["title"])
            .setContentText(messageBody["body"])
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.app_name),
                "CharBot",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
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