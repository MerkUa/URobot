package com.urobot.droid.data.model



data class Chat(
    val chatId: Int,
    val title: String,
    val imageUrl: String,
    val isRead: Boolean,
    val isOnline: Boolean,
    val lastTime: String,
    val details: String
)
