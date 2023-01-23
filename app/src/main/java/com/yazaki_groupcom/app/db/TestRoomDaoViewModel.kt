package com.yazaki_groupcom.app.db

import android.content.Context
import androidx.lifecycle.ViewModel
import com.yazaki_groupcom.app.ThisApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class TestRoomDaoViewModel(context: Context) : ViewModel() {

    private val mTestDao: PostDao by lazy {
        ThisApp.database.postDao()
    }

    fun getListFlow(): Flow<List<Post>> {
        return mTestDao
            .getAllFlow()
            .flowOn(Dispatchers.IO)
    }
}