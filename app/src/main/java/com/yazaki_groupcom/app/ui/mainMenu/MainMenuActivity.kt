package com.yazaki_groupcom.app.ui.mainMenu

import android.content.Intent
import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityMainMenuBinding
import com.yazaki_groupcom.app.ui.main.MainActivity

class MainMenuActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TopMenuActivity"
    }

    private lateinit var binding: ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnHome.setOnClickListener {
            val intent =
                Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

        val intent =
            Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}