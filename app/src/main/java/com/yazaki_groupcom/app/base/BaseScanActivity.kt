package com.yazaki_groupcom.app.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kotlin.concurrent.thread

/**
 * ベースアクティビティ　Scan　Service追加
 * アクティビティ共通処理をここで処理する。
 */
open class BaseScanActivity : BaseActivity() {

    companion object {
        const val TAG: String = "BaseScanActivity"
    }

    private val filter by lazy {
        IntentFilter().apply {
            addAction("unitech.scanservice.data")
        }
    }

    lateinit var baseScanViewModel: BaseScanViewModel

    open val receiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent ?: return
                //スキャン後に取得されたデータ
                if (intent.action.equals("unitech.scanservice.data")) {
                    val barcodeData = intent.getStringExtra("text")
                    Log.e(TAG, "onReceive: barcodeData:$barcodeData")
                    baseScanViewModel.dataText.value = barcodeData
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseScanViewModel = ViewModelProvider(this)[BaseScanViewModel::class.java]
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

    /**
     * Unitech Scan Service の開始
     */
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