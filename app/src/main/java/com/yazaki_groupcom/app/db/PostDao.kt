package com.yazaki_groupcom.app.db

import androidx.room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


//数据库各种操作
@Dao
interface PostDao {
    /**
     * 在 @Dao 注释的 interface 中， vararg 是一种参数修饰符，它可以让一个方法接受多个数量的参数。
     * 例如：postDao.insert(user1, user2, user3)
     */
    @Query("SELECT * FROM posts")
    suspend fun getAll(): List<Post>

    @Query("SELECT * FROM posts")
    fun getAllFlow(): Flow<List<Post>>

    @Query("select * from posts where id = :id")
    suspend fun getSelect(id: Int): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //找到相同的ID序列号，就直接替换
    suspend fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //找到相同的ID序列号，就直接替换
    suspend fun insertAll(postList: List<Post>)

    @Query("SELECT COUNT(*) FROM posts")
    suspend fun count(): Long

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(post: Post)


    @ExperimentalCoroutinesApi
    suspend fun getAllDistinctUntilChanged() = getAllFlow().distinctUntilChanged()
}