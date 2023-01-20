package com.yazaki_groupcom.app

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestSqlBinding
import com.yazaki_groupcom.app.db.UserData
import kotlinx.coroutines.launch

class TestSqlActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestSqlActivity"
    }
    private lateinit var binding: ActivityTestSqlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestSqlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLoad.setOnClickListener {
            lifecycleScope.launch{
                Log.e(TAG, "tvLoad: "+getData() )
            }
        }
        binding.tvSave.setOnClickListener {
            lifecycleScope.launch{
                val userData = UserData(
                    0
                    , binding.userId.text.toString()
                    , binding.userName.text.toString()
                    , binding.roleId.text.toString()
                    , binding.password.text.toString()
                    , binding.insertUser.text.toString()
                    , binding.insertTime.text.toString()
                )
                insert(userData)
            }

        }
    }
}