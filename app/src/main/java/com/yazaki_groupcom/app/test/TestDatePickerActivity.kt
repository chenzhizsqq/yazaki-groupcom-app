package com.yazaki_groupcom.app.test

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestDatePickerBinding


class TestDatePickerActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestDatePickerActivity"
    }

    private lateinit var binding: ActivityTestDatePickerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestDatePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}