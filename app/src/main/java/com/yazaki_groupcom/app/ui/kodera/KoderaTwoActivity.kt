package com.yazaki_groupcom.app.ui.kodera

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.ThisApp
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseScanActivity
import com.yazaki_groupcom.app.databinding.ActivityKoderaTwoBinding
import com.yazaki_groupcom.app.databinding.ActivityMainKoderaBinding
import com.yazaki_groupcom.app.enum.Equipment
import com.yazaki_groupcom.app.enum.ShareKey
import com.yazaki_groupcom.app.ui.first.FirstActivity
import com.yazaki_groupcom.app.ui.processManage.ProcessManageActivity

class KoderaTwoActivity : BaseScanActivity() {

    companion object {
        const val TAG: String = "KoderaTwoActivity"
    }

    //activity_main_kodera.xml
    private lateinit var binding: ActivityKoderaTwoBinding

    //与MainKoderaActivity共同的ViewModel
    private val viewModel: KoderaViewModel by viewModels()

    //最後に選択したプロセス
    private lateinit var lastSelectedProcessName :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKoderaTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //获取上一个Activity传过来的数据
        getExtra()

        binding.returnHome.setOnClickListener {
            returnBack()
        }

        binding.tvUsername.text = currentUserName

        //最後に選択したプロセス
        lastSelectedProcessName = Tools.sharedPreGetString(ShareKey.LastSelectedProcessName.key)
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

                ThisApp.finishActivities()
            }
            builder.setNegativeButton(resources.getString(R.string.bt_logout_cancel)) { dialog, which ->
                // 点击 Cancel 按钮的回调
            }
            val dialog = builder.create()
            dialog.show()
        }

        viewModel.idFragment.observe(this) {
            Log.e(MainKoderaActivity.TAG, "onCreate: !!! idFragment:$it", )
            when(it){
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
            Log.e(MainKoderaActivity.TAG, "!!! QR:$it ")

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
        binding.KoderaActivityTitle.text = Tools.sharedPreGetString(ShareKey.LastSelectedProcessName.key)

    }

    private fun switchToFragmentOne() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, KoderaOneFragment.newInstance())
            .commit()
        binding.tvTitleTips.text = "切断指示を確認して、設備に入力して下さい。"
    }

    private fun switchToFragmentTwo() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, KoderaTwoFragment.newInstance())
            .commit()
        binding.tvTitleTips.text = "部材照合して下さい。"
    }

    private fun switchToFragmentThree() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, KoderaThreeFragment.newInstance())
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