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


class TestScannerSettingsActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestScannerSettingsActivity"
    }

    private val filter = IntentFilter("unitech.scanservice.data")

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action == "unitech.scanservice.data") {
                val barcodeData = intent.getStringExtra("text")
                Log.e(TAG, "onReceive: barcodeData:$barcodeData", )
                Toast.makeText(context, "onReceive: barcodeData:$barcodeData", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val runnable = Runnable {
        try {
            Log.e(TAG, "runnable start"  )
            startUSS()
            Thread.sleep(500) // USS の開始を 500 ミリ秒以上待機します
            closeScanToKey()
            Log.e(TAG, "runnable close"  )
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private lateinit var binding: ActivityTestScannerSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestScannerSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runnable.run()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    private fun startUSS() {
        val intent = Intent()
        intent.action = "unitech.scanservice.start"
        sendBroadcast(intent)
    }

    private fun closeScanToKey() {
        val bundle = Bundle()
        bundle.putBoolean("scan2key", false)
        val intent = Intent()
        intent.action = "unitech.scanservice.scan2key_setting"
        intent.putExtras(bundle)
        sendBroadcast(intent)
    }
}