package com.urobot.droid.data.model

import retrofit2.http.Url


data class Chat(
        val chatId: String,
        val title: String,
        val imageUrl: String,
        val isRead: Boolean,
        val isOnline: Boolean,
        val lastTime: String,
        val details: String
)
