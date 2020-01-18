package com.urobot.droid.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BotDao {

    @Insert
    fun insertBot(bot: BotInfo?)

    @Update
    fun updateBot(bot: BotInfo?)

    @Query("SELECT * from bot_table WHERE bot_id = :botId")
    fun getById(botId: Int?) : BotInfo?


    @Query("SELECT bot_id from bot_table WHERE messenger_id = 1")
    fun getTelegramBotId() : BotInfo?

}