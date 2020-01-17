package com.urobot.droid.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "first_name") var fName: String?,
    @ColumnInfo(name = "last_name") var lName: String?,
    @ColumnInfo(name = "token") var token: String?,
    @ColumnInfo(name = "cellPhone") var cellPhone: String?,
    @ColumnInfo(name = "photoURL") var photoURL: String?

)
