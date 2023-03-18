package com.yazaki_groupcom.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.yazaki_groupcom.app.db.AppDatabase

class ThisApp : Application() {

    val appViewModel: AppViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(
            this
        ).create(AppViewModel::class.java)
    }

    companion object {
        const val TAG: String = "ThisApp"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        lateinit var sharedPreferences: SharedPreferences

        //本地数据库
        lateinit var database: AppDatabase

        //本地数据库
        fun localDBCreate(context: Context) {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    Config.databaseName
                )
                    .fallbackToDestructiveMigration()   //直接删除原有的数据库表并重新创建。
                    .build()
            }
        }

        //存活的activities
        val activities = mutableListOf<Activity>()

        /**
         * 清除所有存活的activities
         */
        fun finishActivities(){
            try {
                for (activity in activities) {
                    activity.finish()
                }
            } catch (e: Exception) {
                Log.e(TAG, "finishActivities: ",e )
            }
        }

        /**
         * 共有keyコミットのString
         *
         * @param key:String
         * @param value:String
         */
        fun sharedPrePut(key: String, value: String) {
            val editor = sharedPreferences.edit()
            editor.apply {
                putString(key, value)
            }.apply()
        }

        /**
         * 共有keyコミットのint
         *
         * @param key:String
         * @param value:Int
         */
        fun sharedPrePut(key: String, value: Int) {
            val editor = sharedPreferences.edit()
            editor.apply {
                putInt(key, value)
            }.apply()
        }

        /**
         * 共有keyコミットのBoolean
         *
         * @param key:String
         * @param value:Boolean
         */
        fun sharedPrePut(key: String, value: Boolean) {
            val editor = sharedPreferences.edit()
            editor.apply {
                putBoolean(key, value)
            }.apply()
        }


        /**
         * 共有keyコミットのLong
         *
         * @param key:String
         * @param value:Long
         */
        fun sharedPrePut(key: String, value: Long) {
            val editor = sharedPreferences.edit()
            editor.apply {
                putLong(key, value)
            }.apply()
        }

        /**
         * 删除共享key
         *
         * @param key
         */
        fun sharedPreRemove(key: String) {
            val editor = sharedPreferences.edit()
            editor.remove(key)
            editor.apply()
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        localDBCreate(this)
    }
}