package com.urobot.droid.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable


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

//    @Query("SELECT token FROM user_table")
//    fun getToken(): User

}