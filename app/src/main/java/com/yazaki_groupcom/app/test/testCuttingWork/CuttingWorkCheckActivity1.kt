package com.yazaki_groupcom.app.test.testCuttingWork

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityCuttingWorkCheckBinding

/**
 * 切断作業チェック
 */
class CuttingWorkCheckActivity1 : BaseActivity() {

    companion object {
        const val TAG: String = "CuttingWorkCheckActivity"
    }

    private lateinit var binding: ActivityCuttingWorkCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuttingWorkCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}