package com.yazaki_groupcom.app.ui.acQrLogin

import android.os.Bundle
import android.widget.Toast
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityAcQrLoginBinding

class AcQrLoginActivity : BaseActivity() {

    companion object {
        const val TAG: String = "AcQrLoginActivity"
    }

    private lateinit var binding: ActivityAcQrLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcQrLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ScanContractの結果を受け取る
        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result ->
            if (result.contents == null) {
                // ここでQRコードを読み取れなかった場合の処理を書く
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG)
                    .show()
            } else {
                // ここでQRコードを読み取れた場合の処理を書く
                // ここではトーストに結果を表示するだけ
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                    .show()
            }
        }


        // QRコードリーダーの立ち上げ
        fun onButtonClick() {
            // 縦画面に固定
            val options = ScanOptions().setOrientationLocked(false)
            barcodeLauncher.launch(options)
        }

        binding.etQrCode.setOnClickListener {
            onButtonClick()
        }
    }
}