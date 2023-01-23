package com.yazaki_groupcom.app.db

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<User>
}