package com.yazaki_groupcom.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "FirstActivity"
    }
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent =
            Intent(this@FirstActivity, AcQrLoginActivity::class.java)
        startActivity(intent)

        binding.MainActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.RfidLoginActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, RfidLoginActivity::class.java)
            startActivity(intent)
        }
        binding.PwLoginActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, PwLoginActivity::class.java)
            startActivity(intent)
        }
        binding.CuttingWorkCheckActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, CuttingWorkCheckActivity::class.java)
            startActivity(intent)
        }
        binding.TopMenuActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TopMenuActivity::class.java)
            startActivity(intent)
        }
        binding.AcQrLoginActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, AcQrLoginActivity::class.java)
            startActivity(intent)
        }



    }
}