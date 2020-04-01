package com.urobot.android.data.model

import com.stfalcon.chatkit.commons.models.IUser


class Author(
        private val id: String,
        private val name: String,
        private val avatar: String,
        val isOnline: Boolean) : IUser {

    override fun getId(): String {
        return id
    }

    override fun getName(): String {
        return name
    }

    override fun getAvatar(): String {
        return avatar
    }

    fun getIsOnline(): Boolean {
        return isOnline
    }

}