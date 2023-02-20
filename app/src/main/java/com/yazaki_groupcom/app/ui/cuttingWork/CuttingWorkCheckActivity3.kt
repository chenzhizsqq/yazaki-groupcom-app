package com.yazaki_groupcom.app.ui.cuttingWork

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityCuttingWorkCheck3Binding

class CuttingWorkCheckActivity3 : BaseActivity() {

    companion object {
        const val TAG: String = "CuttingWorkCheckActivity3"
    }

    private lateinit var binding: ActivityCuttingWorkCheck3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuttingWorkCheck3Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}