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
import com.yazaki_groupcom.app.databinding.ActivityTestRetrofitBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestRetrofitViewModel : ViewModel() {

    val strData = MutableLiveData("strData")

    //专门对应json数据中的posts数据List
    val postsDataList = MutableLiveData<List<PostsData>>()
}

data class PostsData(val id: Int, val title: String)

class TestRetrofitActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "TestRetrofitActivity"
    }

    private lateinit var binding: ActivityTestRetrofitBinding
    private lateinit var viewModel: TestRetrofitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel = ViewModelProvider(this)[TestRetrofitViewModel::class.java]


        viewModel.strData.observe(this) {
            binding.tvTest.text = it
        }
        viewModel.postsDataList.observe(this) {
            it.forEach { postsData ->
                Log.e(TAG, "onCreate: postsData.id - " + postsData.id)
                Log.e(TAG, "onCreate: postsData.title - " + postsData.title)
            }
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                val api = retrofit.create(API::class.java)
                val response = api.getResponseGson()
                // Do something with the users

                if (response.isSuccessful) {
                    viewModel.strData.postValue(response.body().toString())
                    viewModel.postsDataList.postValue(response.body())
                }
            }
        }
    }
}