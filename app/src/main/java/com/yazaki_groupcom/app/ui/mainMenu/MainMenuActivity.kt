package com.yazaki_groupcom.app.ui.mainMenu

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityMainMenuBinding

class MainMenuActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TopMenuActivity"
    }

    private lateinit var binding: ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}