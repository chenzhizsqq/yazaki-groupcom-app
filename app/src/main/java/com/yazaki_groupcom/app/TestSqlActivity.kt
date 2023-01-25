package com.yazaki_groupcom.app

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestSqlBinding

class TestSqlActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestSqlActivity"
    }

    private lateinit var binding: ActivityTestSqlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestSqlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLoad.setOnClickListener {

        }
        binding.tvSave.setOnClickListener {

        }
    }
}