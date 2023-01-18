package com.yazaki_groupcom.app

import android.content.Intent
import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityPwLoginBinding

class PwLoginActivity : BaseActivity() {
    companion object {
        const val TAG: String = "PwLoginActivity"
    }
    private lateinit var binding: ActivityPwLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnHome.setOnClickListener {
            val intent =
                Intent(this@PwLoginActivity, MainActivity::class.java)
            startActivity(intent)
//            overridePendingTransition( android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
            finish()
        }
    }
}