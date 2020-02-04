package com.urobot.droid.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update


@Dao
interface IndustryDao {

    @Insert
    fun insertIndustry(data: Industry?)

    @Update
    fun updateBot(industry: Industry?)

}