package com.yazaki_groupcom.app.db

import androidx.room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface UserDao {
    /**
     * User - m_user
     */


    @Query("SELECT * FROM m_user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM m_user")
    fun getAllFlow(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //找到相同的ID序列号，就直接替换
    suspend fun insert(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( userList: List<User>)

    @Query("SELECT COUNT(*) FROM m_user")
    suspend fun count(): Long

    @Update
    suspend fun update(vararg user: User)

    @Delete
    suspend fun delete(vararg user: User)

    @Query("delete FROM m_user")
    suspend fun deleteAll()

    @ExperimentalCoroutinesApi
    suspend fun getAllDistinctUntilChanged() = getAllFlow().distinctUntilChanged()
}