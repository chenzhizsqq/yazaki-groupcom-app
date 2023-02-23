package com.yazaki_groupcom.app.test

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import com.yazaki_groupcom.app.test.*
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.databinding.ActivityTestMainBinding
import com.yazaki_groupcom.app.db.TestRoomDaoActivity
import com.yazaki_groupcom.app.testScan.TestScanActivity
import com.yazaki_groupcom.app.ui.acQrLogin.AcQrLoginActivity
import com.yazaki_groupcom.app.ui.acSelect.AcSelectActivity
import com.yazaki_groupcom.app.ui.acUpdate.AcUpdateActivity
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkActivity
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkCheckActivity
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkCheckActivity2
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkCheckActivity3
import com.yazaki_groupcom.app.ui.main.MainActivity
import com.yazaki_groupcom.app.ui.pwLogin.PwLoginActivity
import com.yazaki_groupcom.app.ui.rfidLogin.RfidLoginActivity
import com.yazaki_groupcom.app.ui.mainMenu.MainMenuActivity

class TestMainActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestDatePickerActivity"
    }

    private lateinit var binding: ActivityTestMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 縦画面
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        // タイトルバー非表示
        supportActionBar?.hide()

        //FLAG_KEEP_SCREEN_ON  一直开着屏幕的设定
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //以下都是测试的
        test()
    }

    /**
     * 以下都是测试的
     */
    private fun test() {
        val intent =
            Intent(this@TestMainActivity, MainMenuActivity::class.java)
        startActivity(intent)

        binding.MainActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.RfidLoginActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, RfidLoginActivity::class.java)
            startActivity(intent)
        }
        binding.PwLoginActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, PwLoginActivity::class.java)
            startActivity(intent)
        }
        binding.CuttingWorkCheckActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, CuttingWorkCheckActivity::class.java)
            startActivity(intent)
        }
        binding.CuttingWorkCheckActivity2.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, CuttingWorkCheckActivity2::class.java)
            startActivity(intent)
        }
        binding.CuttingWorkCheckActivity3.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, CuttingWorkCheckActivity3::class.java)
            startActivity(intent)
        }
        binding.MainMenuActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, MainMenuActivity::class.java)
            startActivity(intent)
        }
        binding.AcQrLoginActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, AcQrLoginActivity::class.java)
            startActivity(intent)
        }
        binding.AcSelectActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, AcSelectActivity::class.java)
            startActivity(intent)
        }
        binding.AcUpdateActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, AcUpdateActivity::class.java)
            startActivity(intent)
        }

        binding.TestOldSqlActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestMvvmSqlActivity::class.java)
            startActivity(intent)
        }
        binding.TestSqlActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestSqlActivity::class.java)
            startActivity(intent)
        }
        binding.TestRoomDaoActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestRoomDaoActivity::class.java)
            startActivity(intent)
        }

        binding.CuttingWorkActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, CuttingWorkActivity::class.java)
            startActivity(intent)
        }

        binding.TestSmartRefreshLayoutActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestSmartRefreshLayoutActivity::class.java)
            startActivity(intent)
        }

        binding.TestRetrofitActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestRetrofitActivity::class.java)
            startActivity(intent)
        }

        binding.TestNfcActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestNfcActivity::class.java)
            startActivity(intent)
        }

        binding.TestScannerSettingsActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestScannerSettingsActivity::class.java)
            startActivity(intent)
        }

        binding.TestScannerSettingsBaseActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestScannerSettingsBaseActivity::class.java)
            startActivity(intent)
        }

        binding.TestScanServiceActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestScanServiceActivity::class.java)
            startActivity(intent)
        }

        binding.TestScanActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestScanActivity::class.java)
            startActivity(intent)
        }
        binding.TestDatePickerActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestDatePickerActivity::class.java)
            startActivity(intent)
        }
        binding.TestButtonActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestButtonActivity::class.java)
            startActivity(intent)
        }
        binding.TestComposeActivity.setOnClickListener {
            val intent =
                Intent(this@TestMainActivity, TestComposeActivity::class.java)
            startActivity(intent)
        }


    }
}