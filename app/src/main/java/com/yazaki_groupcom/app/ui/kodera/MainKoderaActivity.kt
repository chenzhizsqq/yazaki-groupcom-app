package com.yazaki_groupcom.app.ui.kodera

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityMainKoderaBinding
import com.yazaki_groupcom.app.ui.first.FirstActivity
import com.yazaki_groupcom.app.ui.processManage.ProcessManageActivity

class MainKoderaActivity : BaseActivity() {

    companion object {
        const val TAG: String = "MainKoderaActivity"
    }

    //activity_main_kodera.xml
    private lateinit var binding: ActivityMainKoderaBinding

    //与MainKoderaActivity共同的ViewModel
    private val viewModel: KoderaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKoderaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnHome.setOnClickListener {
            returnBack()
        }

        binding.btLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.bt_logout_title))
            builder.setMessage(resources.getString(R.string.bt_logout_message))
            builder.setPositiveButton(resources.getString(R.string.bt_logout_ok)) { dialog, which ->
                // 点击 OK 按钮的回调
                val intent =
                    Intent(this, FirstActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton(resources.getString(R.string.bt_logout_cancel)) { dialog, which ->
                // 点击 Cancel 按钮的回调
            }
            val dialog = builder.create()
            dialog.show()
        }

        switchToFragmentOne()

        viewModel.idFragment.observe(this) {
            Log.e(TAG, "onCreate: !!! idFragment:$it", )
            when(it){
                "1" -> {
                    switchToFragmentOne()
                }
                "2" -> {
                    switchToFragmentTwo()
                }
            }
        }
    }

    private fun switchToFragmentOne() {
        val koderaOneFragment = KoderaOneFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, koderaOneFragment)
            .commit()
    }

    private fun switchToFragmentTwo() {
        val koderaTwoFragment = KoderaTwoFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, koderaTwoFragment)
            .commit()
    }


    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

        returnBack()
    }

    /**
     * 返回键的操作
     */
    private fun returnBack() {
        val intent =
            Intent(this, ProcessManageActivity::class.java)
        startActivity(intent)
        finish()
    }
}