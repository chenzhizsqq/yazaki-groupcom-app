package com.yazaki_groupcom.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityPwLoginBinding
import androidx.appcompat.widget.PopupMenu
import com.yazaki_groupcom.app.db.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PwLoginActivity : BaseActivity(),PopupMenu.OnMenuItemClickListener {
    companion object {
        const val TAG: String = "PwLoginActivity"
    }
    private lateinit var binding: ActivityPwLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnHome.setOnClickListener {
            val intent =
                Intent(this@PwLoginActivity, MainActivity::class.java)
            startActivity(intent)
//            overridePendingTransition( android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
            finish()
        }

        binding.etId.setOnClickListener {
            openMenu(it,R.menu.menu_items)
        }

        binding.btIdLogin.setOnClickListener {
            Tools.showErrorDialog(this,"入力されたIDまたは\nパスワードが正しくありません")
        }

        //添加的测试begin
        binding.btTestAll.setOnClickListener {
            dataGetAll()
        }
        binding.btTestInsert.setOnClickListener {
            dataInsert(
                "0"
                ,"name"
                ,binding.etId.text.toString()
                ,binding.etPw.text.toString()
                ,"user"
                ,"time")
        }
        binding.btTestCheck.setOnClickListener {
            getUsersList("id", "ps")
        }
        //添加的测试end
    }

    /**
     * 入力内容に合致する作業者IDを表示
     */
    private fun openMenu(anchor: View, resId: Int) {
        val popup = PopupMenu(this, anchor)
        popup.inflate(resId)
        popup.setOnMenuItemClickListener(this)             // リスナーの登録
        popup.show()
    }

    /**
     * メニュークリック
     */
    override fun onMenuItemClick(item: MenuItem): Boolean { // リスナーの実装
        return when (item.itemId) {
            R.id.menu_item1 -> {
                Log.e(TAG, "!!! onMenuItemClick: 1" )
                true
            }
            R.id.menu_item2 -> {
                Log.e(TAG, "!!! onMenuItemClick: 2" )
                true
            }
            R.id.menu_item3 -> {
                Log.e(TAG, "!!! onMenuItemClick: 3" )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun dataInsert(user_id: String?,
                           user_name: String?,
                           role_id: String?,
                           password: String?,
                           insert_user: String?,
                           insert_time: String?,) {

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()

            var user = User(
                userDao.count()+1,
                user_id,
                user_name,
                role_id,
                password,
                insert_user,
                insert_time,)

            userDao.insert(user)
        }
    }

    /**
     *
    @Query("select * from posts where user_id = :user_id and user_name = :user_name")
    suspend fun getSelect(user_id: String,user_name:String): List<User>
     */
    private fun getUsersList(user_id: String, user_name: String){

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()

            val getSelect =  userDao.getSelect(user_id, user_name)

            withContext(Dispatchers.Main) {
                Log.e(TAG, "getUsersList: $getSelect", )
            }
        }
    }


    private fun dataGetAll() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val userDao = ThisApp.database.userDao()

            var userList = userDao.getAll()

            withContext(Dispatchers.Main) {
                Log.e(TAG, "dataGetAll: userList:$userList")
            }
        }
    }
}