package com.yazaki_groupcom.app.ui.pwLogin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.ThisApp
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.base.BaseScanActivity
import com.yazaki_groupcom.app.databinding.ActivityPwLoginBinding
import com.yazaki_groupcom.app.db.User
import com.yazaki_groupcom.app.ui.main.MainActivity
import com.yazaki_groupcom.app.ui.mainMenu.MainMenuActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        //ヒントメニューのデータの取得
        getMenuData()

//        binding.returnHome.setOnClickListener {
//            val intent =
//                Intent(this@PwLoginActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        binding.etId.setOnClickListener {
            openMenu(it, R.menu.menu_items)
        }

        binding.btIdLogin.setOnClickListener {
            Tools.showErrorDialog(this, "入力されたIDまたは\nパスワードが正しくありません")
        }

        //添加的测试begin
        binding.dataGetAll.setOnClickListener {
            dataGetAll()
        }
        binding.dataInsert.setOnClickListener {
            dataInsert(
                binding.etId.text.toString(),
                binding.etId.text.toString(),
                binding.etId.text.toString(),
                binding.etPw.text.toString(),
                binding.etId.text.toString(),
                "time"
            )
        }
        binding.getUsersList.setOnClickListener {
            getUsersList(binding.etId.text.toString(), binding.etPw.text.toString())
        }
        binding.deleteAll.setOnClickListener {
            deleteAll()
        }
        //添加的测试end

        //扫码功能，通过扫码能够知道id和密码。
        //スキャン後に取得されたデータ
        baseScanViewModel.dataText.observe(this) {
            Log.e(MainActivity.TAG, "!!! QR:$it ", )
            binding.etId.setText(it)
            binding.etPw.setText(it)

            // ***为了测试，能够扫描一秒后马上跳去MainMenuActivity
            if (it.isNotBlank() && it.isNotEmpty()){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1000) // 1000表示延时1秒钟
            }
        }
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
     * データ挿入ユーザーリスト
     */
    private fun dataInsert(
        user_id: String?,
        user_name: String?,
        role_id: String?,
        password: String?,
        insert_user: String?,
        insert_time: String?,
    ) {

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()
            val user = User(
                userDao.count(),
                user_id,
                user_name,
                role_id,
                password,
                insert_user,
                insert_time,
            )

            userDao.insert(user)
        }
    }

    /**
     *データ取得ユーザーのリスト
     */
    private fun getUsersList(user_id: String, password: String) {

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()

            val getSelectList = userDao.getSelectList(user_id, password)

            withContext(Dispatchers.Main) {
                Log.e(TAG, "getUsersList: $getSelectList")

                when (getSelectList.count()) {
                    0 -> {

                        Tools.showErrorDialog(this@PwLoginActivity, "getUsersList: 0")
                    }
                    1 -> {

                        Tools.showErrorDialog(this@PwLoginActivity, "getUsersList: ok")
                    }
                    else -> {

                        Tools.showErrorDialog(this@PwLoginActivity, "getUsersList: 大于1")
                    }
                }
            }
        }
    }


    private fun dataGetAll() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()

            val userList = userDao.getAll()

            withContext(Dispatchers.Main) {
                Tools.showErrorDialog(this@PwLoginActivity, "userList:$userList")
                Log.e(TAG, "dataGetAll: userList:$userList")
            }
        }
    }

    /**
     * ヒントメニューのデータの取得
     */
    private fun getMenuData() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()

            val userList = userDao.getAll()

            withContext(Dispatchers.Main) {
                //データ消去
                menuStringList.clear()

                //データ設定
                var i = 0
                userList.forEach lit@{
                    if (i >= 3) {
                        return@lit
                    }
                    it.role_id?.let { it1 -> menuStringList.add(it1) }
                    i++
                }
                for (j in 0..2) {
                    if (menuStringList.count() < 3) {
                        menuStringList.add("user_$j")
                    }
                }
            }
        }
    }

    /**
     * データをすべて削除
     */
    private fun deleteAll() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()

            val userList = userDao.deleteAll()

            withContext(Dispatchers.Main) {
                Log.e(TAG, "deleteAll: userList:$userList")
            }
        }
    }

    /**
     * 携帯電話のリターンボタンをクリックすると特定のActivityにジャンプします。
     */
    override fun onBackPressed() {
        super.onBackPressed()

        val intent =
            Intent(this@PwLoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}