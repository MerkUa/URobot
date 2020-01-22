package com.urobot.droid.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bot_table")
data class BotInfo(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "bot_id") var botId : Int?,
    @ColumnInfo(name = "messenger_id") var messengerId : Int?,
    @ColumnInfo(name = "messenger_name") var messengerName : String?
    )


enum class Messenger(val messengerId: Int) {
    Telegram(1),
    Viber(2),
    Facebook(3),
    Instagram(4),
    Vk(5),
    WhatsApp(6)
}


