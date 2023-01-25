package com.yazaki_groupcom.app.ui.acUpdate

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityAcUpdateBinding

class AcUpdateActivity : BaseActivity() {

    companion object {
        const val TAG: String = "AcUpdateActivity"
    }

    private lateinit var binding: ActivityAcUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}