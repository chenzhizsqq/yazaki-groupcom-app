package com.yazaki_groupcom.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.yazaki_groupcom.app.databinding.ActivityFirstBinding
import com.yazaki_groupcom.app.databinding.ActivityTestScanServiceBinding
import com.yazaki_groupcom.app.ui.first.FirstViewModel

class TestScanServiceActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestScanServiceActivity"
    }

    private lateinit var binding: ActivityTestScanServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestScanServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btTest.setOnClickListener {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
        }
    }
}