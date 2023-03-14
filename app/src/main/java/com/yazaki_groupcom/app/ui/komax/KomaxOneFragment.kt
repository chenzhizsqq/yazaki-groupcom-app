package com.yazaki_groupcom.app.ui.komax

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.databinding.FragmentKoderaOneBinding
import java.util.ArrayList

class KomaxOneFragment : Fragment() {

    companion object {
        const val TAG: String = "KomaxOneFragment"
        fun newInstance() = KomaxOneFragment()
    }

    private lateinit var binding: FragmentKoderaOneBinding

    //Adapter
    private lateinit var mAdapter: KomaxOneAdapter
    val list = ArrayList<KomaxOneData>()

    //与MainActivity共同的ViewModel
    val sharedVM: KomaxViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKoderaOneBinding.inflate(inflater, container, false)

        //Adapter setting
        mAdapter = KomaxOneAdapter(requireActivity())
        mAdapter.notifyDataSetChanged(list)
        binding.recyclerViewKoderaOne.adapter = mAdapter
        mAdapter.setOnAdapterListener(object :KomaxOneAdapter.OnAdapterListener{
            override fun onClick(id: Int) {
                Log.e(TAG, "onClick: id:$id", )
                sharedVM.idFragment.value = 2
            }

            /**
             * "检查"　"切断完了"　ボタンの設定
             */
            override fun onCheck(id: Int) {
                Log.e(TAG, "onCheck: id:$id", )
            }
        })

        //Adapter data
        addData()
        addData()
        addData()
        addData()

        return binding.root
    }

    private fun addData() {
        val koderaOneData = KomaxOneData(list.count(),list.count().toString())
        mAdapter.notifyDataSetAdd(koderaOneData)
    }


}