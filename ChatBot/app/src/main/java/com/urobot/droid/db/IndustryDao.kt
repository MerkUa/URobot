package com.urobot.droid.db

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.DELETE


@Dao
interface IndustryDao {

    @Insert
    fun insertIndustry(data: Industry?)

    @Update
    fun updateBot(industry: Industry?)

    @Query("SELECT * FROM industry_table ")
    fun getAllIndustry(): List<Industry>

    @Delete
    fun delete(industry: List<Industry?>)

}