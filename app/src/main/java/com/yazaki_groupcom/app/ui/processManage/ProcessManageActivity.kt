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
import kotlin.collections.ArrayList

class ProcessManageActivity : BaseScanActivity() {

    companion object {
        const val TAG: String = "ProcessManageActivity"
    }

    //activity_process_manage.xml  進捗管理
    private lateinit var binding: ActivityProcessManageBinding

    //ll_titles の　タイトル
    private lateinit var titleTvList: ArrayList<TextView>
    private lateinit var mAdapter: ProcessTitleAdapter

    var processDataArray = ArrayList<ProcessData>()

    //ProcessViewModel
    lateinit var viewModel: ProcessViewModel

    private val finishReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            finish()
        }
    }

    private var lastPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(finishReceiver, IntentFilter("ProcessManageActivity"))

        viewModel = ViewModelProvider(this)[ProcessViewModel::class.java]

        //ll_titles の　タイトル
        mAdapter = ProcessTitleAdapter(processDataArray,this)
        binding.rvRecord.adapter = mAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecord.layoutManager = layoutManager

        mAdapter.setOnAdapterListener(object : ProcessTitleAdapter.OnAdapterListener {
            override fun onClick(selectName: String, dateTime: String, position: Int) {
                Log.e(TAG, "onClick: $selectName", )
                binding.tvDateTime.text = dateTime
                lastPosition = position

                Tools.sharedPrePut(Config.lastSelectedProcessName,selectName)
                mAdapter.notifyDataSetChanged(processDataArray)
            }
        })

        binding.tvUsername.text = currentUserName

        binding.tvTest.setOnClickListener {
            val random = Random()
            val now = Tools.getNow()
            processDataArray.add(ProcessData(
                processDataArray.count()
                ,processDataArray.count().toString()
                ,now
                ,getDateTime()
                ,random.nextInt(100000)
                ,random.nextInt(100000)
                ,random.nextInt(100))
            )
            mAdapter.notifyDataSetChanged(processDataArray)

            viewModel.isUpdated.postValue(true)

            binding.tvDateTime.text = now

            mAdapter.notifyDataSetChanged(processDataArray)
        }

        binding.returnHome.setOnClickListener {
            val intent =
                Intent(this, MainMenuActivity::class.java)
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

        binding.btUpdate.setOnClickListener {
            dataUpdate()

            if (lastPosition>-1){
                val random = Random()
                val data = ProcessData(
                    processDataArray[lastPosition].index
                    ,processDataArray[lastPosition].title
                    ,Tools.getNow()
                    ,getDateTime()
                    ,random.nextInt(100000)
                    ,random.nextInt(100000)
                    ,random.nextInt(100)
                )
                binding.tvDateTime.text = data.data
                processDataArray[lastPosition]= data
            }
            mAdapter.notifyDataSetChanged(processDataArray)
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

    /**
     * mvvmの設定
     */
    private fun mvvmSetting() {

        //扫码后
        viewModel.isUpdated.observe(this) {
            if (it) {
                //tvHint
                binding.tvHint.visibility = View.INVISIBLE

                //ns_main
                binding.nsMain.visibility = View.VISIBLE
            } else {
                //tvHint
                binding.tvHint.visibility = View.VISIBLE

                //ns_main
                binding.nsMain.visibility = View.INVISIBLE
            }
        }

        //スキャン後に取得されたデータ
        baseScanViewModel.dataText.observe(this) { it ->
            Log.e(TAG, "!!! QR:$it ")

            if (it.isNotBlank() && it.isNotEmpty()) {
                dataUpdate()

                //C375,C,01
                val newString = it.replace(",", "-")
                if (!isTvListContainName(newString)){
                    for (titleTv in titleTvList) {
                        if (titleTv.visibility == View.GONE && titleTv.text != newString) {
                            if (isTvListAllGone()){
                                Tools.sharedPrePut(Config.lastSelectedProcessName,newString)
                            }

                            titleTv.text = newString
                            titleTv.visibility = View.VISIBLE
                            break
                        }
                    }
                }
            }
        }



        mAdapter.notifyDataSetChanged(processDataArray)
    }

    private fun getDateTime(): String {
        var strGetDataTime = ""
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
        val currentDate = Date()
        val formattedDate = dateFormat.format(currentDate)
        strGetDataTime = "123：$formattedDate"
        return strGetDataTime
    }

    //titleTvList里，是否包含那个名字
    private fun isTvListContainName(newString: String): Boolean {
        for (titleTv in titleTvList) {
            if (titleTv.text == newString) {
                return true
            }
        }
        return false
    }

    //titleTvList里，是否全都隐藏
    private fun isTvListAllGone(): Boolean {
        for (titleTv in titleTvList) {
            if (titleTv.visibility != View.GONE){
                return false
            }
        }
        return true
    }

    private fun dataUpdate() {
        viewModel.isUpdated.postValue(true)
    }

    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

        val intent =
            Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}