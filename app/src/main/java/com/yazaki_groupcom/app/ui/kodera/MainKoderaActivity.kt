package com.yazaki_groupcom.app.ui.kodera

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
import com.yazaki_groupcom.app.enum.ShareKey
import com.yazaki_groupcom.app.ui.first.FirstActivity
import com.yazaki_groupcom.app.ui.processManage.ProcessManageActivity

class MainKoderaActivity : BaseScanActivity() {

    companion object {
        const val TAG: String = "MainKoderaActivity"
    }

    //activity_main_kodera.xml
    private lateinit var binding: ActivityMainKoderaBinding

    //与MainKoderaActivity共同的ViewModel
    private val viewModel: KoderaViewModel by viewModels()

    //最後に選択したプロセス
    private lateinit var lastSelectedProcessName :String

    //Adapter
    private lateinit var mAdapter: KoderaOneAdapter
    val listKoderaData = java.util.ArrayList<KoderaEachData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKoderaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Adapter setting
        mAdapter = KoderaOneAdapter(this, java.util.ArrayList<KoderaEachData>())
        binding.recyclerViewKoderaOne.adapter = mAdapter
        mAdapter.setOnAdapterListener(object :KoderaOneAdapter.OnAdapterListener{
            override fun onClick(id: Int) {
                Log.e(TAG, "onClick: id:$id", )

                viewModel.koderaEachData.postValue(listKoderaData[id])
            }

            /**
             * "检查"　"切断完了"　ボタンの設定
             */
            override fun onCheck(id: Int) {
                Log.e(TAG, "onCheck: id:$id", )
            }
        })

        testData()
        testData2()
        testData()
        testData2()

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

                sendBroadcast(Intent("ProcessManageActivity"))
            }
            builder.setNegativeButton(resources.getString(R.string.bt_logout_cancel)) { dialog, which ->
                // 点击 Cancel 按钮的回调
            }
            val dialog = builder.create()
            dialog.show()
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
        binding.KoderaActivityTitle.text = Tools.sharedPreGetString(ShareKey.LastSelectedProcessName.key)

    }


    private fun testData() {
        //Adapter data

        val koderaOneData = KoderaEachData(
            mAdapter.list.count(),
            "",
            "ー",
            "40",
            "200",
            "2023/02/01",

            "CI001",
            "CI00123021010",
            "1R7",
            "041",
            "40",
            "2096",
            "80",

            0,
            1,
            1,
            1,
            0,

            false
        )
        listKoderaData.add(koderaOneData)
        addData(listKoderaData[listKoderaData.count()-1])
    }

    private fun testData2() {
        //Adapter data


        val koderaOneData = KoderaEachData(
            mAdapter.list.count(),
            "",
            "ー",
            "40",
            "200",
            "2023/02/01",

            "CI001",
            "CI00123021010",
            "1R7",
            "041",
            "40",
            "2096",
            "80",

            2,
            0,
            0,
            0,
            2,

            false
        )
        listKoderaData.add(koderaOneData)
        addData(listKoderaData[listKoderaData.count()-1])
    }


    private fun addData(koderaOneData : KoderaEachData) {
        mAdapter.notifyDataSetAdd(koderaOneData)
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