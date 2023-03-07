package com.yazaki_groupcom.app.ui.processManage

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
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

    //ProcessViewModel
    lateinit var viewModel: ProcessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ll_titles の　タイトル
        titleInit()

        binding.tvUsername.text = currentUserName

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
        }

        binding.btNext.setOnClickListener {
            val intent =
                Intent(this, MainKoderaActivity::class.java)
            startActivity(intent)
            finish()
        }

        //观察ll_titles的成员，是否点中
        for (i in 0 until binding.llTitles.childCount) {
            val view = binding.llTitles.getChildAt(i)
            view.setOnClickListener {

                allTitlesNotClicked()

                if (view is TextView) {
                    //android:background="@drawable/bg_layout"
                    view.setBackgroundResource(R.drawable.ic_round_button_orange)

                    //android:textColor="@color/black"
                    view.setTextColor(Color.WHITE)

                    Tools.sharedPrePut(Config.lastSelectedProcessName,view.text.toString())

                    dataUpdate()
                }
            }
        }

        //mvvmの設定
        mvvmSetting()
    }

    //ll_titles の　タイトル
    private fun titleInit() {
        titleTvList = ArrayList<TextView>()
        titleTvList.addAll(
            listOf(
                binding.tvEquipment0,
                binding.tvEquipment1,
                binding.tvEquipment2,
                binding.tvEquipment3,
                binding.tvEquipment4,
                binding.tvEquipment5,
                binding.tvEquipment6,
                binding.tvEquipment7,
                binding.tvEquipment8,
                binding.tvEquipment9,
                binding.tvEquipment10,
                binding.tvEquipment11,
                binding.tvEquipment12,
                binding.tvEquipment13,
                binding.tvEquipment14,
                binding.tvEquipment15,
                binding.tvEquipment16,
                binding.tvEquipment17,
                binding.tvEquipment18,
                binding.tvEquipment19,
                binding.tvEquipment20,
            )
        )
        titleTvList.onEach {
            it.visibility = View.GONE
        }
    }

    /**
     * mvvmの設定
     */
    private fun mvvmSetting() {
        viewModel = ViewModelProvider(this)[ProcessViewModel::class.java]

        //扫码后
        viewModel.isUpdated.observe(this) {
            if (it) {
                //tvHint
                binding.tvHint.visibility = View.INVISIBLE

                //hs_title
                binding.hsTitle.visibility = View.VISIBLE
                //ns_main
                binding.nsMain.visibility = View.VISIBLE

                val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
                val currentDate = Date()
                val formattedDate = dateFormat.format(currentDate)
                binding.tvDateTime.text = "取得タイミング：$formattedDate"
            } else {
                //tvHint
                binding.tvHint.visibility = View.VISIBLE

                //hs_title
                binding.hsTitle.visibility = View.INVISIBLE
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

    //全部title没有点中。
    private fun allTitlesNotClicked() {
        val linearLayout = binding.llTitles
        for (i in 0 until linearLayout.childCount) {
            val view = linearLayout.getChildAt(i)

            if (view is TextView) {
                //android:background="@drawable/bg_layout"
                view.setBackgroundResource(R.drawable.bg_layout)

                //android:textColor="@color/black"
                view.setTextColor(Color.BLACK)
            }

        }
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