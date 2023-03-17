package com.yazaki_groupcom.app.ui.kodera

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.databinding.FragmentKoderaOneBinding
import java.util.ArrayList

class KoderaOneFragment : Fragment() {

    companion object {
        const val TAG: String = "KoderaOneFragment"
        fun newInstance() = KoderaOneFragment()
    }

    private lateinit var binding: FragmentKoderaOneBinding

    //Adapter
    private lateinit var mAdapter: KoderaOneAdapter
    val listKoderaData = ArrayList<KoderaEachData>()

    //与MainActivity共同的ViewModel
    val sharedVM: KoderaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKoderaOneBinding.inflate(inflater, container, false)

        //Adapter setting
        mAdapter = KoderaOneAdapter(requireActivity(),ArrayList<KoderaEachData>())
        binding.recyclerViewKoderaOne.adapter = mAdapter
        mAdapter.setOnAdapterListener(object :KoderaOneAdapter.OnAdapterListener{
            override fun onClick(id: Int) {
                Log.e(TAG, "onClick: id:$id", )
                sharedVM.idFragment.value = 2

                sharedVM.koderaEachData.postValue(listKoderaData[id])
            }

            /**
             * "检查"　"切断完了"　ボタンの設定
             */
            override fun onCheck(id: Int) {
                Log.e(TAG, "onCheck: id:$id", )
            }
        })

        testData()
        testData()
        testData()
        testData()

        return binding.root
    }

    private fun testData() {
        //Adapter data

        val koderaOneData = KoderaEachData(
            mAdapter.itemCount,
            "",
            "ー",
            "40",
            "200",
            "2023/02/01",

            "CI001",
            "CI00123021010",
            "1R7",
            "041",
            "40",
            "2096",
            "80",

            1,
            1,
            1,
            1,
            1,

            false
        )
        listKoderaData.add(koderaOneData)
        addData(listKoderaData[listKoderaData.count()-1])
    }

    private fun addData(koderaOneData : KoderaEachData) {
        mAdapter.notifyDataSetAdd(koderaOneData)
    }


}