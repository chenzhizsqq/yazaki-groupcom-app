package com.yazaki_groupcom.app.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.databinding.ActivityTestScannerSettingsBinding
import kotlin.concurrent.thread


class TestScannerSettingsActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestScannerSettingsActivity"
    }

    private val filter by lazy {
        IntentFilter().apply {
            addAction("unitech.scanservice.data")
        }
    }

    private val receiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent ?: return
                //扫描后获取的数据
                if (intent.action.equals("unitech.scanservice.data")) {
                    val barcodeData = intent.getStringExtra("text")
                    Log.e(TAG, "onReceive: barcodeData:$barcodeData")
                    Toast.makeText(context, "接收的条形码或二维码:\n $barcodeData", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private lateinit var binding: ActivityTestScannerSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestScannerSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //runnable.run()
        thread(start = true) {
            try {
                startScanService()
                Thread.sleep(500) // USS の開始を 500 ミリ秒以上待機します
                closetScanToKey()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    private fun startScanService() {
        Intent().also { intent ->
            intent.setAction("unitech.scanservice.start")
            sendBroadcast(intent)
        }
    }

    private fun closetScanToKey() {
        Intent().also { intent ->
            intent.setAction("unitech.scanservice.scan2key_setting")
            intent.putExtras(Bundle().apply {
                putBoolean("scan2key", false)
            })
            sendBroadcast(intent)
        }
    }
}