package com.yazaki_groupcom.app.ui.first

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yazaki_groupcom.app.*
import com.yazaki_groupcom.app.databinding.ActivityFirstBinding
//import com.yazaki_groupcom.app.db.TestRoomDaoActivity
import com.yazaki_groupcom.app.testScan.TestScanActivity
import com.yazaki_groupcom.app.ui.acQrLogin.AcQrLoginActivity
import com.yazaki_groupcom.app.ui.acSelect.AcSelectActivity
import com.yazaki_groupcom.app.ui.acUpdate.AcUpdateActivity
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkActivity
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkCheckActivity
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkCheckActivity2
import com.yazaki_groupcom.app.ui.main.MainActivity
import com.yazaki_groupcom.app.ui.pwLogin.PwLoginActivity
import com.yazaki_groupcom.app.ui.rfidLogin.RfidLoginActivity
import com.yazaki_groupcom.app.ui.topMenu.TopMenuActivity

class FirstActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "FirstActivity"
    }

    private lateinit var binding: ActivityFirstBinding
    private lateinit var viewModel:FirstViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //設定画面を開いたままにする
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 縦画面
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        // タイトルバー非表示
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[FirstViewModel::class.java]

        //loading状態のmvvm設定。
        viewModel.isLoading.observe(this, Observer {
            it?.let {
                if (it) {
                    // 表示ロード
                    binding.llProgressbar.visibility = View.VISIBLE
                } else {
                    // ロードの非表示
                    binding.llProgressbar.visibility = View.GONE
                }
            }
        })

        //以下都是测试的
        test()

    }

    /**
     * 以下都是测试的
     */
    private fun test() {
        val intent =
            Intent(this@FirstActivity, TestOldSqlActivity::class.java)
        startActivity(intent)

        binding.llMain.setOnClickListener {
            viewModel.isLoading.value = viewModel.isLoading.value != true
        }

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
        binding.CuttingWorkCheckActivity2.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, CuttingWorkCheckActivity2::class.java)
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
        binding.AcSelectActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, AcSelectActivity::class.java)
            startActivity(intent)
        }
        binding.AcUpdateActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, AcUpdateActivity::class.java)
            startActivity(intent)
        }

        binding.TestOldSqlActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TestOldSqlActivity::class.java)
            startActivity(intent)
        }
        binding.TestSqlActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TestSqlActivity::class.java)
            startActivity(intent)
        }
//        binding.TestRoomDaoActivity.setOnClickListener {
//            val intent =
//                Intent(this@FirstActivity, TestRoomDaoActivity::class.java)
//            startActivity(intent)
//        }

        binding.CuttingWorkActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, CuttingWorkActivity::class.java)
            startActivity(intent)
        }

        binding.TestSmartRefreshLayoutActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TestSmartRefreshLayoutActivity::class.java)
            startActivity(intent)
        }

        binding.TestRetrofitActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TestRetrofitActivity::class.java)
            startActivity(intent)
        }

        binding.TestNfcActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TestNfcActivity::class.java)
            startActivity(intent)
        }

        binding.TestScanServiceActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TestScanServiceActivity::class.java)
            startActivity(intent)
        }

        binding.TestScanActivity.setOnClickListener {
            val intent =
                Intent(this@FirstActivity, TestScanActivity::class.java)
            startActivity(intent)
        }



    }
}