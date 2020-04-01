package com.urobot.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "industry_table")
data class Industry(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: String

)

