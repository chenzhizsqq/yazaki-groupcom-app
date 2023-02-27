package com.yazaki_groupcom.app.test.testCuttingWork

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.databinding.ActivityCuttingWorkCheck2Binding

class CuttingWorkCheckActivity2 : AppCompatActivity() {
    companion object {
        const val TAG: String = "CuttingWorkCheckActivity2"
    }

    private lateinit var binding: ActivityCuttingWorkCheck2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuttingWorkCheck2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 縦画面
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        // タイトルバー非表示
        supportActionBar?.hide()
    }
}