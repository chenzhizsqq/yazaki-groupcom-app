package com.yazaki_groupcom.app.dbMath

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MathScoreDbViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG: String = "MathScoreDbViewModel"
    }
    private val dao: MathScoreDao

    private val getFlow: Flow<List<MathScore>>
    init {
        val db = MathScoreDB.getInstance(application) // DBにアクセスするclassで一度だけDBをビルドする
        dao = db.mathScoreDao() // 使用するDaoを指定

        getFlow = dao.getAllFlow()
        getFlow.onEach {
            //do something with each user list
            Log.e(TAG, "init : getFlow:$it", )
            liveListMathScore.value = it
        }.launchIn(viewModelScope)
    }
    var liveListMathScore= MutableLiveData<List<MathScore>>()


    fun insert(mathScore: MathScore) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertAll(mathScore)
        }
    }

    fun selectGetAll(): List<MathScore> {
        return dao.getAll()
    }

    fun getMaxScore(date: String, id: String): Int {
        return dao.getMaxScore(date, id)
    }

    fun getMaxScoreLive(date: String, id: String): LiveData<Int> {
        return dao.getMaxScoreLive(date, id)
    }

    fun getFlowData(): Flow<List<MathScore>> {
        return getFlow
    }

    val plantsFlow: Flow<List<MathScore>>
        get() = dao.getAllFlow()

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }


}