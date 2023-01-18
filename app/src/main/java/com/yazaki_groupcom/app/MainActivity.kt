package com.yazaki_groupcom.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    companion object {
        const val TAG: String = "MainActivity"
    }
    private lateinit var binding: ActivityMainBinding

    private lateinit var qrScanIntegrator: IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etQrCode.setOnClickListener {
            qrScan()
        }

        binding.tvQrMessage.setOnClickListener {
            qrScan()
        }

        binding.btIdLogin.setOnClickListener {

        }

        binding.btRfid.setOnClickListener {
            val intent =
                Intent(this@MainActivity, RfidLoginActivity::class.java)
            startActivity(intent)
//            overridePendingTransition( android.R.anim.slide_out_right,
//                android.R.anim.slide_in_left);
            finish()
        }
    }

    //QR SCAN
    private fun qrScan() {
        this.qrScanIntegrator = IntentIntegrator(this)
        // 縦画面に固定
        this.qrScanIntegrator.setOrientationLocked(false)
        // QRコード読み取り後のビープ音を停止
        this.qrScanIntegrator.setBeepEnabled(false)
        // スキャン開始
        this.qrScanIntegrator.initiateScan()
    }

    // 読取後に呼ばれる
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            // 読取結果はresult.contentsで参照できる
            Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
            binding.etQrCode.setText(result.contents)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}