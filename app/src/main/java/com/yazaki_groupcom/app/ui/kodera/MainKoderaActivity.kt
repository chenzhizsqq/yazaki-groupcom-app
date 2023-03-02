package com.yazaki_groupcom.app.ui.kodera

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityMainKoderaBinding
import com.yazaki_groupcom.app.ui.first.FirstActivity
import com.yazaki_groupcom.app.ui.processManage.ProcessManageActivity
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainKoderaActivity : BaseActivity() {

    companion object {
        const val TAG: String = "MainKoderaActivity"
    }

    //activity_main_kodera.xml
    private lateinit var binding: ActivityMainKoderaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKoderaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnHome.setOnClickListener {
            val intent =
                Intent(this, ProcessManageActivity::class.java)
            startActivity(intent)
            finish()
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

        // 生成条形码
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap("1234567890", BarcodeFormat.CODE_128, 1400, 300)
        binding.ivTest1.setImageBitmap(bitmap)
        binding.ivTest2.setImageBitmap(bitmap)
        binding.ivTest3.setImageBitmap(bitmap)
        binding.ivTest4.setImageBitmap(bitmap)
    }


    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

        val intent =
            Intent(this, ProcessManageActivity::class.java)
        startActivity(intent)
        finish()
    }
}