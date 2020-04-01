package com.urobot.android.db

import androidx.room.*


@Dao
interface IndustryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIndustry(data: Industry?)

    @Update
    fun updateBot(industry: Industry?)

    @Query("SELECT * FROM industry_table ")
    fun getAllIndustry(): List<Industry>

    @Delete
    fun delete(industry: List<Industry?>)

}