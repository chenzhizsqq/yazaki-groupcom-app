package com.yazaki_groupcom.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.yazaki_groupcom.app.databinding.ActivityTestSmartRefreshLayoutBinding
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkAdapter
import com.yazaki_groupcom.app.ui.cuttingWork.CuttingWorkData

class TestSmartRefreshLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestSmartRefreshLayoutBinding
    private lateinit var mAdapter: CuttingWorkAdapter
    //情報データアレイ
    private var dataArray = ArrayList<CuttingWorkData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestSmartRefreshLayoutBinding.inflate(layoutInflater)
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

        //smartRefreshLayout 的 Setting
        smartRefreshLayoutSetting()
    }

    //smartRefreshLayout 的 Setting
    private fun smartRefreshLayoutSetting() {
        //设置 Header 为 贝塞尔雷达 样式
        //refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        val mBezierRadarHeader = BezierRadarHeader(this)
        mBezierRadarHeader.setEnableHorizontalDrag(true)
        binding.refreshLayout.setRefreshHeader(mBezierRadarHeader)
        //设置 Footer 为 球脉冲 样式
        //refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        val mBallPulseFooter = BallPulseFooter(this)
        mBallPulseFooter.spinnerStyle = SpinnerStyle.Scale
        binding.refreshLayout.setRefreshFooter(mBallPulseFooter)
    }


    private fun test() {
        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))
    }
}