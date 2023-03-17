package com.yazaki_groupcom.app.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yazaki_groupcom.app.API
import com.yazaki_groupcom.app.databinding.ActivityTestRetrofit2Binding
import com.yazaki_groupcom.app.databinding.ActivityTestRetrofitBinding
import com.yazaki_groupcom.app.ui.rfidLogin.RfidLoginActivity
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TestRetrofitActivity2 : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestRetrofitActivity2"
    }

    private lateinit var binding: ActivityTestRetrofit2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestRetrofit2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://192.168.1.49:44304/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val api = retrofit.create(API::class.java)
                    val response = api.getResponse()
                    // Do something with the users

                    if (response.isSuccessful) {
                        Log.e(TAG, "onCreate: "+response.body().toString() )
                    }
                }catch (e : Exception){
                    Log.e(TAG, "onCreate: retrofit", e)
                }
            }
        }
    }
}