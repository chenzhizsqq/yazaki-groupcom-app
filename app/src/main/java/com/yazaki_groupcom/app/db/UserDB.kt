package com.yazaki_groupcom.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//数据库的接口
@Database(entities = [MathScore::class], version = 1)
abstract class UserDB : RoomDatabase() {
    abstract fun mathScoreDao(): MathScoreDao


    companion object {
        private const val DATABASE_NAME = "m_user"
        private lateinit var userDB: UserDB

        //注意：如果您的应用在单个进程中运行，在实例化 AppDatabase 对象时应遵循单例设计模式。
        //每个 RoomDatabase 实例的成本相当高，而您几乎不需要在单个进程中访问多个实例
        fun getInstance(context: Context): UserDB {
            if (!this::userDB.isInitialized) {
                //创建的数据库的实例
                userDB = Room.databaseBuilder(
                    context.applicationContext,
                    userDB::class.java,
                    DATABASE_NAME
                ).build()
            }
            return userDB
        }

    }
}