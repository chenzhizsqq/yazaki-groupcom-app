package com.yazaki_groupcom.app

import android.content.Intent
import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityFirstBinding

class FirstActivity : BaseActivity() {
    companion object {
        const val TAG: String = "FirstActivity"
    }
    private lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent =
            Intent(this@FirstActivity, MainActivity::class.java)
        startActivity(intent)

        binding.MainActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.CuttingWorkCheckActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, CuttingWorkCheckActivity::class.java)
            startActivity(intent)
        }


    }
}