package com.yazaki_groupcom.app.db

import androidx.room.*

//数据库各种操作
@Dao
interface UserDao {
    @Query("SELECT * FROM m_user")
    fun getAll(): List<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg post: UserData)

    @Update
    suspend fun update(vararg users: UserData)

    @Delete
    suspend fun delete(post: UserData)

    @Query("delete FROM m_user")
    suspend fun deleteAll()
}