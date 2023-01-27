package com.yazaki_groupcom.app

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestNfcBinding

class TestNfcActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestNfcActivity"
    }

    private lateinit var binding: ActivityTestNfcBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestNfcBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}