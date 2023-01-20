package com.yazaki_groupcom.app.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserDbViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: UserDao

    init {
        val db = UserDB.getInstance(application) // DBにアクセスするclassで一度だけDBをビルドする
        dao = db.userDao() // 使用するDaoを指定
    }


    fun insert(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertAll(userData)
        }
    }

    fun update(users: UserData){
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(users)
        }
    }

    suspend fun selectGetAll(): List<UserData> {
        return dao.getAll()
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }


}