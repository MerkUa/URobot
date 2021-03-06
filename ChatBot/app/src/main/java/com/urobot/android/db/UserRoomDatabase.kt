package com.urobot.android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

// Annotates class to be a Room Database with a table (entity) of the User class
@Database(entities = [User::class, BotInfo::class, Industry::class], version = 2, exportSchema = false)
public abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun botDao(): BotDao
    abstract fun industryDao() : IndustryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(
                context: Context
        ): UserRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_database"
                ).addCallback(UserDatabaseCallback()).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class UserDatabaseCallback(
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                    populateDatabase(database.userDao())

            }
        }

         fun populateDatabase(userDao: UserDao) {
            // Delete all content here.
//            userDao.deleteAll()
//            Log.d("populateDatabase","populateDatabase ")
//
//            var user = User("950950cc-e8f4-47d0-abf4-8a8a67796df6","Sherlock Platform","User")
//            userDao.insert(user)

        }
    }
}