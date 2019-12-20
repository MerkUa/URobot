package com.urobot.droid.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
        @PrimaryKey val id: String,
        @ColumnInfo(name = "name") var name: String?,
        @ColumnInfo(name = "token") var token: String?,
        @ColumnInfo(name = "cellPhone") var cellPhone: String?,
        @ColumnInfo(name = "photoURL") var photoURL: String?

)
