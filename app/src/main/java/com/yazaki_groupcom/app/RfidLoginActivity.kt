package com.yazaki_groupcom.app

import android.content.Intent
import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityRfidLoginBinding

class RfidLoginActivity : BaseActivity() {
    companion object {
        const val TAG: String = "RfidLoginActivity"
    }
    private lateinit var binding: ActivityRfidLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRfidLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnHome.setOnClickListener {
            val intent =
                Intent(this@RfidLoginActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition( android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
            finish()
        }
    }
}