package com.yazaki_groupcom.app

import android.os.Bundle
import androidx.activity.viewModels
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestOldSqlBinding
import com.yazaki_groupcom.app.dbMath.MathScoreDbViewModel

class TestOldSqlActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestSqlActivity"
    }

    private lateinit var binding: ActivityTestOldSqlBinding

    val mathScoreDbViewModel: MathScoreDbViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestOldSqlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLoad.setOnClickListener {

        }
        binding.tvSave.setOnClickListener {

        }
        binding.cleanData.setOnClickListener {
            mathScoreDbViewModel.deleteAll()
        }
    }
}