package com.yazaki_groupcom.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m_user")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "user_id") val user_id: String?,
    @ColumnInfo(name = "user_name") val user_name: String?,
    @ColumnInfo(name = "role_id") val role_id: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "insert_user") val insert_user: String?,
    @ColumnInfo(name = "insert_time") val insert_time: String?,
)