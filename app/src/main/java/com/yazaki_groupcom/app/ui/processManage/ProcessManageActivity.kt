package com.yazaki_groupcom.app.ui.processManage

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseScanActivity
import com.yazaki_groupcom.app.databinding.ActivityProcessManageBinding
import com.yazaki_groupcom.app.ui.first.FirstActivity
import com.yazaki_groupcom.app.ui.kodera.MainKoderaActivity
import com.yazaki_groupcom.app.ui.mainMenu.MainMenuActivity
import java.text.SimpleDateFormat
import java.util.*

class ProcessManageActivity : BaseScanActivity() {

    companion object {
        const val TAG: String = "ProcessManageActivity"
    }

    //activity_process_manage.xml  進捗管理
    private lateinit var binding: ActivityProcessManageBinding

    private var arrayListProcessData = ArrayList<ProcessData>()

    //ProcessViewModel
    lateinit var viewModel: ProcessViewModel

    private val finishReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            finish()
        }
    }

    var lastSelectTitleIndex = -1

    //Adapter
    private lateinit var titleAdapter: ProcessTitleAdapter

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(finishReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProcessViewModel::class.java]

        //虚拟的数据
        virtualData()

        //title Adapter setting
        titleAdapter = ProcessTitleAdapter(this)
        binding.rvRecord.adapter = titleAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecord.layoutManager = layoutManager
        titleAdapter.setOnAdapterListener(object :ProcessTitleAdapter.OnAdapterListener{
            override fun onClick(index: Int) {
                Log.e(TAG, "onClick: index:$index", )

                lastSelectTitleIndex = index

                for (i in 0 until binding.rvRecord.childCount) {
                    val view = binding.rvRecord.getChildAt(i)

                    if (view is TextView) {
                        //android:background="@drawable/bg_layout"
                        view.setBackgroundResource(R.drawable.bg_layout)

                        //android:textColor="@color/black"
                        view.setTextColor(Color.BLACK)

                        if (index == i){
                            view.setBackgroundResource(R.drawable.ic_round_button_orange)

                            //android:textColor="@color/black"
                            view.setTextColor(Color.WHITE)
                        }

                        Tools.sharedPrePut(Config.lastSelectedProcessName, view.text.toString())

                        binding.infoDate.text = arrayListProcessData[index].info_date
                        binding.infoJisai.text = arrayListProcessData[index].info_jisai
                        binding.infoZhishi.text = arrayListProcessData[index].info_zhishi
                        binding.infoJinbu.text = arrayListProcessData[index].info_jinbu
                        binding.tvDateTime.text =  arrayListProcessData[index].data
                    }
                }
            }
        })

        registerReceiver(finishReceiver, IntentFilter("ProcessManageActivity"))

        if (Config.isCheckMode){
            binding.tvTitle.setOnClickListener {
                val intent =
                    Intent(this, MainKoderaActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btEquipmentConfirm.setOnClickListener {
            if (!binding.etEquipmentId.text.isNullOrEmpty()){
                Tools.sharedPrePut(Config.lastSelectedProcessName,binding.etEquipmentId.text.toString())
                val intent =
                    Intent(this, MainKoderaActivity::class.java)
                startActivity(intent)
            }
        }

        binding.tvUsername.text = currentUserName

        binding.returnHome.setOnClickListener {
            if(viewModel.isUpdated.value == false){
                val intent =
                    Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent =
                    Intent(this, ProcessManageActivity::class.java)
                startActivity(intent)
                finish()
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
            }
            builder.setNegativeButton(resources.getString(R.string.bt_logout_cancel)) { dialog, which ->
                // 点击 Cancel 按钮的回调
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.btUpdate.setOnClickListener {
            viewModel.isUpdated.postValue(true)
        }

        binding.btNext.setOnClickListener {
            val intent =
                Intent(this, MainKoderaActivity::class.java)
            startActivity(intent)
            //finish()
        }

        //mvvmの設定
        mvvmSetting()
    }

    //假的数据
    private fun virtualData() {

    }

    /**
     * mvvmの設定
     */
    private fun mvvmSetting() {

        //扫码后
        viewModel.isUpdated.observe(this) {
            Log.e(TAG, "mvvmSetting: isUpdated : $it", )
            if (it) {
                //tvHint
                binding.tvHint.visibility = View.INVISIBLE
                //ll_equipment
                binding.llEquipment.visibility = View.INVISIBLE

                //ns_main
                binding.nsMain.visibility = View.VISIBLE

                arrayListProcessData[lastSelectTitleIndex].data = getDataTime()

                binding.infoDate.text = arrayListProcessData[lastSelectTitleIndex].info_date
                binding.infoJisai.text = arrayListProcessData[lastSelectTitleIndex].info_jisai
                binding.infoZhishi.text = arrayListProcessData[lastSelectTitleIndex].info_zhishi
                binding.infoJinbu.text = arrayListProcessData[lastSelectTitleIndex].info_jinbu
                binding.tvDateTime.text =  arrayListProcessData[lastSelectTitleIndex].data

            } else {
                //tvHint
                binding.tvHint.visibility = View.VISIBLE
                //ll_equipment
                binding.llEquipment.visibility = View.VISIBLE

                //ns_main
                binding.nsMain.visibility = View.INVISIBLE

                arrayListProcessData.clear()
            }
        }

        //スキャン後に取得されたデータ
        baseScanViewModel.dataText.observe(this) { it ->
            Log.e(TAG, "!!! QR:$it ")

            if (it.isNotBlank() && it.isNotEmpty() && !isHaveTitle(it)) {

                //C373,C,01
                val newString = it.replace(",", "-")

                val mProcessData = ProcessData(it,getDataTime(),"0"+(1..9).random() + "/"+(10..19).random()
                    ,(123..10000).random().toString()+"本"
                    ,(123..10000).random().toString()+"本"
                    ,(1..99).random().toString()+"%"
                )
                arrayListProcessData.add(mProcessData)

                if (lastSelectTitleIndex == -1){
                    lastSelectTitleIndex = 0
                    binding.infoDate.text = arrayListProcessData[lastSelectTitleIndex].info_date
                    binding.infoJisai.text = arrayListProcessData[lastSelectTitleIndex].info_jisai
                    binding.infoZhishi.text = arrayListProcessData[lastSelectTitleIndex].info_zhishi
                    binding.infoJinbu.text = arrayListProcessData[lastSelectTitleIndex].info_jinbu
                    binding.tvDateTime.text = arrayListProcessData[lastSelectTitleIndex].data
                }

                titleAdapter.notifyDataSetAdd(newString)

                viewModel.isUpdated.postValue(true)
            }
        }
    }

    private fun isHaveTitle(title:String):Boolean{
        arrayListProcessData.forEach {
            if (it.title == title){
                return true
            }
        }
        return false
    }

    private fun getDataTime(): String {
        var getDataTime = ""
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val currentDate = Date()
        getDataTime = dateFormat.format(currentDate)
        return "取得タイミング：" + getDataTime
    }

    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

        if(viewModel.isUpdated.value == false){
            val intent =
                Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent =
                Intent(this, ProcessManageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}