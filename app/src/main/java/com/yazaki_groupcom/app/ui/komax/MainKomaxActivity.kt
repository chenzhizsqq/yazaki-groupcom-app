package com.yazaki_groupcom.app.ui.komax

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseScanActivity
import com.yazaki_groupcom.app.databinding.ActivityMainKoderaBinding
import com.yazaki_groupcom.app.enum.Equipment
import com.yazaki_groupcom.app.ui.first.FirstActivity

class MainKomaxActivity : BaseScanActivity() {

    companion object {
        const val TAG: String = "MainKomaxActivity"
    }

    //activity_main_kodera.xml
    private lateinit var binding: ActivityMainKoderaBinding

    //与MainKoderaActivity共同的ViewModel
    private val viewModel: KomaxViewModel by viewModels()

    //idFragmentのリスト
    private val listFrag = ArrayList<String>()

    //最後に選択したプロセス
    private lateinit var lastSelectedProcessName :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKoderaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //获取上一个Activity传过来的数据
        getExtra()

        binding.returnHome.setOnClickListener {
            returnBack()
        }

        binding.tvUsername.text = currentUserName

        //最後に選択したプロセス
        lastSelectedProcessName = Tools.sharedPreGetString(Config.lastSelectedProcessName)
        if (lastSelectedProcessName.length>=4){
            if (lastSelectedProcessName.substring(0,4)== Equipment.C373.code){
                viewModel.strDuanzi.value = Equipment.C373.explain
            }
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

                sendBroadcast(Intent("ProcessManageActivity"))
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
                1 -> {
                    switchToFragmentOne()
                }
                2 -> {
                    switchToFragmentTwo()
                }
                3 -> {
                    switchToFragmentThree()
                }
            }
        }

        //スキャン後に取得されたデータ
        baseScanViewModel.dataText.observe(this) {
            Log.e(TAG, "!!! QR:$it ")

            // ***为了测试
            if (it.isNotBlank() && it.isNotEmpty()) {
                viewModel.scanDataText.value = it
            }
        }
    }

    /**
     * 获取上一个Activity传过来的数据
     */
    private fun getExtra() {
        //("KoderaActivity_title", "C385-01")
        binding.KoderaActivityTitle.text = Tools.sharedPreGetString(Config.lastSelectedProcessName)

    }

    private fun switchToFragmentOne() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, KomaxOneFragment.newInstance())
            .commit()
        binding.tvTitleTips.text = "切断指示を確認して、設備に入力して下さい。"
    }

    private fun switchToFragmentTwo() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, KomaxTwoFragment.newInstance())
            .commit()
        binding.tvTitleTips.text = "部材照合して下さい。"
    }

    private fun switchToFragmentThree() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, KomaxThreeFragment.newInstance())
            .commit()
        binding.tvTitleTips.text = "測定値を入力して下さい。"
    }


    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {

//        //当idFragment回到1后，倒退按键才是退出去。要不然一直减1
//        if(viewModel.idFragment.value!! > 1 ){
//            viewModel.idFragment.value = viewModel.idFragment.value!! -1
//        }else{
//            super.onBackPressed()
//            returnBack()
//        }
        super.onBackPressed()
        returnBack()
    }

    /**
     * 返回键的操作
     */
    private fun returnBack() {
//        val intent =
//            Intent(this, ProcessManageActivity::class.java)
//        startActivity(intent)
        finish()
    }
}