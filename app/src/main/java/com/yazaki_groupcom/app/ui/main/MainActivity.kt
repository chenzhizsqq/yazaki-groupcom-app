package com.yazaki_groupcom.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.yazaki_groupcom.app.base.BaseScanActivity
import com.yazaki_groupcom.app.databinding.ActivityMainBinding
import com.yazaki_groupcom.app.ui.mainMenu.MainMenuActivity
import com.yazaki_groupcom.app.ui.pwLogin.PwLoginActivity


class MainActivity : BaseScanActivity() {

    companion object {
        const val TAG: String = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btIdLogin.setOnClickListener {
            val intent =
                Intent(this@MainActivity, PwLoginActivity::class.java)
            startActivity(intent)
//            overridePendingTransition( android.R.anim.slide_out_right,
//                android.R.anim.slide_in_left);
            finish()
        }

//        binding.btRfid.setOnClickListener {
//            val intent =
//                Intent(this@MainActivity, RfidLoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        //スキャン後に取得されたデータ
        baseScanViewModel.dataText.observe(this) {
            Log.e(TAG, "!!! QR:$it ")
            binding.etQrCode.setText(it)

            // ***为了测试，能够扫描一秒后马上跳去MainMenuActivity
            if (it.isNotBlank() && it.isNotEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1000) // 1000表示延时1秒钟
            }
        }

    }

}