package com.yazaki_groupcom.app.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yazaki_groupcom.app.databinding.ActivityTestScanServiceBinding
import com.yazaki_groupcom.app.databinding.ActivityTestScannerSettingsBinding

class TestScannerSettingsActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestScannerSettingsActivity"
    }

    private lateinit var binding: ActivityTestScannerSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestScannerSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}