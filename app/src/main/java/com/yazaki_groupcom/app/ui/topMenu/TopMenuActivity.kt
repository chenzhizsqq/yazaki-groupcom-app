package com.yazaki_groupcom.app.ui.topMenu

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTopMenuBinding

class TopMenuActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TopMenuActivity"
    }

    private lateinit var binding: ActivityTopMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}