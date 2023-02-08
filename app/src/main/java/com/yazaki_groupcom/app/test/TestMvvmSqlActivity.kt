package com.yazaki_groupcom.app.test

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestMvvmSqlBinding
import com.yazaki_groupcom.app.dbMath.MathScore
import com.yazaki_groupcom.app.dbMath.MathScoreDbViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestMvvmSqlActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestOldSqlActivity"
    }

    private val mathScoreDbViewModel: MathScoreDbViewModel by viewModels()
    private lateinit var binding: ActivityTestMvvmSqlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestMvvmSqlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //这里是mvc，按了之后，需要再去处理数据 begin
        binding.selectGetAll.setOnClickListener {
            binding.tvLog.text = ""
            getData()
            Tools.showMsg(binding.root, "selectGetAll")
        }
        binding.btInsert.setOnClickListener {
            val mathScore = MathScore(0, TAG, 2, Tools.getDate(), Tools.getDateTime())
            mathScoreDbViewModel.insert(mathScore)
            Tools.showMsg(binding.root, "btInsert")

            binding.tvLog.text = ""
            getData()

        }
        binding.cleanData.setOnClickListener {
            mathScoreDbViewModel.deleteAll()
            Tools.showMsg(binding.root, "cleanData")

            binding.tvLog.text = ""
            getData()
        }
        //这里是mvc，按了之后，需要再去处理数据 end

        //这个就是直接的mvvm，按了之后，就能够直接处理数据 begin
        mathScoreDbViewModel.liveListMathScore.observe(this) {
            Log.e(TAG, "mvvm !!!************************** mathScoreDbViewModel.liveListMathScore")
            binding.tvLog2.text = it.toString()
        }
        //这个就是直接的mvvm，按了之后，就能够直接处理数据 end

    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            mathScoreDbViewModel.selectGetAll().forEach {
                Log.e(TAG, "mathScoreDbViewModel.selectGetAll: $it")
                binding.tvLog.text = binding.tvLog.text.toString() + it + "\n"
            }
        }
    }
}