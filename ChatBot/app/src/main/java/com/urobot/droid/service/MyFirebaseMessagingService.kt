package com.urobot.droid.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object{
        const val TAG ="FB"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: " + remoteMessage.getFrom())


//        remoteMessage.data
//        if (p0.getData().size > 0) {
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//
//        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body);
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