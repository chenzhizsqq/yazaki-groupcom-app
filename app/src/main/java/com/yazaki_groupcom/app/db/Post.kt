package com.yazaki_groupcom.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//数据库的结构
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "title") val title: String?
)
