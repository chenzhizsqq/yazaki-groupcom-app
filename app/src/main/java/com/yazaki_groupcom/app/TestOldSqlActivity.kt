package com.yazaki_groupcom.app

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestOldSqlBinding
import com.yazaki_groupcom.app.dbMath.MathScore
import com.yazaki_groupcom.app.dbMath.MathScoreDbViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestOldSqlActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestOldSqlActivity"
    }

    private val mathScoreDbViewModel: MathScoreDbViewModel by viewModels()

    private lateinit var binding: ActivityTestOldSqlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestOldSqlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectGetAll.setOnClickListener {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                mathScoreDbViewModel.selectGetAll().forEach {
                    Log.e(TAG, "mathScoreDbViewModel.selectGetAll: $it")
                }
            }
            Tools.showMsg(binding.root,"selectGetAll")
        }
        binding.btInsert.setOnClickListener {
            val mathScore = MathScore(0, TAG, 2, Tools.getDate(), Tools.getDateTime())
            mathScoreDbViewModel.insert(mathScore)
            Tools.showMsg(binding.root,"btInsert")

        }

        binding.cleanData.setOnClickListener {
            mathScoreDbViewModel.deleteAll()
            Tools.showMsg(binding.root,"cleanData")
        }
    }
}