package com.yazaki_groupcom.app.ui.processManage

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.yazaki_groupcom.app.R
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

    var KoderaActivity_title = "C385-01"
    var duanzi_value = "シース剥ぎ寸法"

    //ProcessViewModel
    lateinit var viewModel: ProcessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            intent.putExtra("KoderaActivity_title", KoderaActivity_title)
            intent.putExtra("duanzi_value", duanzi_value)
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

                    KoderaActivity_title = view.text.toString()

                    val firstFourChars = view.text.substring(0, 4)
                    if (firstFourChars == "C385"){
                        duanzi_value = "シース剥ぎ寸法"
                    }
                    if (firstFourChars == "C373"){
                        duanzi_value = "皮むき寸法"
                    }

                    dataUpdate()
                }
            }
        }

        //mvvmの設定
        mvvmSetting()
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
        baseScanViewModel.dataText.observe(this) {
            Log.e(TAG, "!!! QR:$it ")

            // ***为了测试
            if (it.isNotBlank() && it.isNotEmpty()) {
                dataUpdate()
            }
        }
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