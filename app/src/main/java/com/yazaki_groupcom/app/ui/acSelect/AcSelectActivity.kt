package com.yazaki_groupcom.app.ui.acSelect

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityAcSelectBinding

class AcSelectActivity : BaseActivity() {

    companion object {
        const val TAG: String = "AcSelectActivity"
    }

    private lateinit var binding: ActivityAcSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}