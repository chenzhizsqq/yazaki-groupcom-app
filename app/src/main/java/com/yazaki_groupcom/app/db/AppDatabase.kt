package com.yazaki_groupcom.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//数据库的接口
@Database(entities = [Post::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    abstract fun userDao(): UserDao


    companion object {
        private lateinit var mAppDatabase: AppDatabase

        //注意：如果您的应用在单个进程中运行，在实例化 AppDatabase 对象时应遵循单例设计模式。
        //每个 RoomDatabase 实例的成本相当高，而您几乎不需要在单个进程中访问多个实例
        fun getInstance(context: Context): AppDatabase {
            if (!this::mAppDatabase.isInitialized) {
                //创建的数据库的实例
                mAppDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    com.yazaki_groupcom.app.Config.databaseName
                )
                    .fallbackToDestructiveMigration()   //直接删除原有的数据库表并重新创建。
                    .build()
            }
            return mAppDatabase
        }

    }
}