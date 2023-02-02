package com.yazaki_groupcom.app.test

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.databinding.ActivityTestScanServiceBinding

/**
 * 以下都是运用unitech的sdk中的api
 */


class TestScanServiceActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestScanServiceActivity"
    }

    private lateinit var binding: ActivityTestScanServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestScanServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Unitech Scan Serviceの開始
        binding.tvStart.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.start"
                sendBroadcast(intent)
                Toast.makeText(this, "start", Toast.LENGTH_SHORT).show()
            }
        }

        //Scan To Keyを終了する
        binding.tvScan2keyFalse.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.scan2key_setting"
                intent.putExtras(Bundle().apply {
                    putBoolean("scan2key", false)
                })
                sendBroadcast(intent)
                Toast.makeText(this, "scan2key_setting : scan2key, false", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        //Scan To Keyを有効
        binding.tvScan2keyTrue.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.scan2key_setting"
                intent.putExtra("scan2key", true)
                sendBroadcast(intent)
                Toast.makeText(this, "scan2key_setting : scan2key, true", Toast.LENGTH_SHORT).show()
            }
        }

        //Unitech Scan Serviceの終了
        binding.tvClose.setOnClickListener {
            Intent().also { intent ->
                intent.action = "unitech.scanservice.close"
                intent.putExtra("close", true)
                sendBroadcast(intent)
                Toast.makeText(this, "close : close, true", Toast.LENGTH_SHORT).show()
            }
        }
    }
}