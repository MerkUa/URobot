package com.urobot.android.data.model


data class Bot(
    val botId: String,
    val title: String,
    val description: String,
    val listMessengers: List<Int>,
    val details: String,
    val link: String?
)
