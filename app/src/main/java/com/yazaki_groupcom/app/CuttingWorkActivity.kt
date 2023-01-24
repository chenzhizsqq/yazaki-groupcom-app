package com.yazaki_groupcom.app

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityCuttingWorkBinding

class CuttingWorkActivity : BaseActivity() {
    companion object {
        const val TAG: String = "CuttingWorkActivity"
    }
    private lateinit var mAdapter: CuttingWorkAdapter
    private lateinit var binding: ActivityCuttingWorkBinding

    private val dataArray = ArrayList<CuttingWorkData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuttingWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        test()

        mAdapter = CuttingWorkAdapter(dataArray)
        binding.rvRecord.adapter = mAdapter
    }

    private fun test() {
        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))

        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))

        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))

        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))

        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))

        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))

        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))

        dataArray.add(CuttingWorkData("123", "123", "123"))
        dataArray.add(CuttingWorkData("222", "222", "222"))
        dataArray.add(CuttingWorkData("333", "333", "333"))
    }
}