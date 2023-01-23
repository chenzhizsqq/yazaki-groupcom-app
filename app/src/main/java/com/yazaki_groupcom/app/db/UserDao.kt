package com.yazaki_groupcom.app.db

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM m_user")
    suspend fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg post: User)

    @Update
    suspend fun update(vararg users: User)

    @Delete
    suspend fun delete(post: User)

    @Query("delete FROM m_user")
    suspend fun deleteAll()
}