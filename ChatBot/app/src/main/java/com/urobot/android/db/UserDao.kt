package com.urobot.android.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * from user_table LIMIT 1")
    fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user_table WHERE id = :userId LIMIT 1")
    fun getUserById(userId: String): User?

    @Query("SELECT * from user_table ORDER BY ID DESC LIMIT 1")
    fun getCurrentUser(): User?

//    @Query("SELECT token FROM user_table")
//    fun getToken(): User

}