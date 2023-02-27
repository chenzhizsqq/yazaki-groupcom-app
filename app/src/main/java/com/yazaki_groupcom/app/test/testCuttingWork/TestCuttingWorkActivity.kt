package com.yazaki_groupcom.app.test.testCuttingWork

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityCuttingWorkBinding

class TestCuttingWorkActivity : BaseActivity() {
    companion object {
        const val TAG: String = "CuttingWorkActivity"
    }

    private lateinit var mAdapter: CuttingWorkAdapter
    private lateinit var binding: ActivityCuttingWorkBinding

    //情報データアレイ
    private var dataArray = ArrayList<CuttingWorkData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuttingWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        test()

        mAdapter = CuttingWorkAdapter(dataArray)
        binding.rvRecord.adapter = mAdapter

        //スライドによるデータの更新
        binding.rvRecord.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                //最後のローに移動すると、自動的に更新されます
                if (lastVisibleItem >= totalItemCount - 1) {
                    dataArray.add(
                        CuttingWorkData(
                            totalItemCount.toString(),
                            totalItemCount.toString(),
                            totalItemCount.toString()
                        )
                    )
                    mAdapter.notifyDataSetChanged(dataArray)
                }
            }
        })

    }

    private fun test() {
        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))
    }
}