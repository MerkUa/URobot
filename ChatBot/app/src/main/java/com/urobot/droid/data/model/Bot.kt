package com.urobot.droid.data.model

import retrofit2.http.Url


data class Bot(
        val botId: String,
        val title: String,
        val description: String,
        val listMessengers: List<String>,
        val details: String
)
