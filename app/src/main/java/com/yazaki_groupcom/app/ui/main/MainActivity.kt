package com.yazaki_groupcom.app.ui.main

import android.content.Intent
import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseScanActivity
import com.yazaki_groupcom.app.databinding.ActivityMainBinding
import com.yazaki_groupcom.app.ui.pwLogin.PwLoginActivity
import com.yazaki_groupcom.app.ui.rfidLogin.RfidLoginActivity


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
            finish()
        }

        binding.btRfid.setOnClickListener {
            val intent =
                Intent(this@MainActivity, RfidLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //スキャン後に取得されたデータ
        baseScanViewModel.dataText.observe(this) {
            binding.etQrCode.setText(it)
        }

    }

}