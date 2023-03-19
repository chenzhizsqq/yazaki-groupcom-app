package com.yazaki_groupcom.app.ui.pwLogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.ThisApp
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseScanActivity
import com.yazaki_groupcom.app.databinding.ActivityPwLoginBinding
import com.yazaki_groupcom.app.enum.ShareKey
import com.yazaki_groupcom.app.ui.mainMenu.MainMenuActivity

class PwLoginActivity : BaseScanActivity(), PopupMenu.OnMenuItemClickListener {
    companion object {
        const val TAG: String = "PwLoginActivity"
    }

    private lateinit var binding: ActivityPwLoginBinding

    //ヒントメニューのデータ
    var menuStringList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Config.isCheckMode) {
            binding.tvTitle.setOnClickListener {
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btIdLogin.setOnClickListener {
            //Tools.showErrorDialog(this, "入力されたIDまたは\nパスワードが正しくありません")


            if (bCheckIdPw()) {

                if (Tools.sharedPreGetString(ShareKey.CurrentUserName.key).isNullOrBlank()) {
                    Tools.sharedPrePut(ShareKey.CurrentUserName.key, "admin")
                }else{
                    Tools.sharedPrePut(ShareKey.CurrentUserName.key, binding.etId.text.toString())
                }

                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Tools.showAlertDialog(
                    this,
                    "入力エラー",
                    "入力されたIDまたは\nパスワードが正しくありません",
                    null
                )
            }
        }

        //扫码功能，通过扫码能够知道id和密码。
        //スキャン後に取得されたデータ
        baseScanViewModel.dataText.observe(this) {
            Log.e(TAG, "!!! QR:$it ")

            // ***为了测试，能够扫描一秒后马上跳去MainMenuActivity
            if (it.isNotBlank() && it.isNotEmpty()) {
                val inputString = it
                val resultArray = inputString.split(",")
                if (resultArray.size == 3) {
                    binding.etId.setText(resultArray[0])
                    binding.etPw.setText(resultArray[1])

                    Tools.sharedPrePut(ShareKey.CurrentUserName.key, resultArray[0])

                } else {
                    Tools.sharedPrePut(ShareKey.CurrentUserName.key, "")
                }

            }
        }
    }

    private fun bCheckIdPw(): Boolean {

        val etId = binding.etId.text.toString()
        val etPw = binding.etPw.text.toString()
        if (etId.length < 4) {
            return false
        } else {
            //test_add
            if (etId.length == 4){
                return true
            }
            if (etId.length == 8){
                return true
            }
        }
        if (etPw.length < 4) {
            return false
        }

        return false
    }

    /**
     * 入力内容に合致する作業者IDを表示
     */
    private fun openMenu(anchor: View, resId: Int) {
        val popup = PopupMenu(this, anchor)
        popup.inflate(resId)
        popup.setOnMenuItemClickListener(this)             // リスナーの登録

        if (menuStringList.isNotEmpty()) {
            val item1 = popup.menu.findItem(R.id.menu_item1)
            item1.title = menuStringList[0]
        }

        if (menuStringList.count() > 1) {
            val item2 = popup.menu.findItem(R.id.menu_item2)
            item2.title = menuStringList[1]
        }

        if (menuStringList.count() > 2) {
            val item3 = popup.menu.findItem(R.id.menu_item3)
            item3.title = menuStringList[2]
        }

        popup.show()

    }

    /**
     * メニュークリック
     */
    override fun onMenuItemClick(item: MenuItem): Boolean { // リスナーの実装
        return when (item.itemId) {
            R.id.menu_item1 -> {
                binding.etId.setText(item.title)
                true
            }
            R.id.menu_item2 -> {
                binding.etId.setText(item.title)
                true
            }
            R.id.menu_item3 -> {
                binding.etId.setText(item.title)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

//        val intent =
//            Intent(this@PwLoginActivity, MainActivity::class.java)
//        startActivity(intent)
        finish()
    }
}