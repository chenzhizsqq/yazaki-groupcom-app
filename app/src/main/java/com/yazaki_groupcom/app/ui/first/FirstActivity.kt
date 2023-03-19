package com.yazaki_groupcom.app.ui.first

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.databinding.ActivityFirstBinding
import com.yazaki_groupcom.app.ui.pwLogin.PwLoginActivity

class FirstActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "FirstActivity"
    }

    private lateinit var binding: ActivityFirstBinding
    private lateinit var viewModel: FirstViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //FLAG_KEEP_SCREEN_ON  一直开着屏幕的设定
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 縦画面
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        // タイトルバー非表示
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[FirstViewModel::class.java]
        viewModel.isLoading.value = true

        //loading状態のmvvm設定。
        viewModel.isLoading.observe(this) {
            it?.let {
                if (it) {
                    // 表示ロード
                    binding.llProgressbar.visibility = View.VISIBLE
                } else {
                    // ロードの非表示
                    binding.llProgressbar.visibility = View.GONE
                }
            }
        }

        //去到第一个页面上
        val intent =
            Intent(this@FirstActivity, PwLoginActivity::class.java)
        startActivity(intent)
        finish()


    }
}