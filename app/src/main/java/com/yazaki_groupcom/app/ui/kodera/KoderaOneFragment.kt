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


    private lateinit var koderaOneAdapter: KoderaOneAdapter

    val list = ArrayList<KoderaOneData>()

    //与MainActivity共同的ViewModel
    val sharedVM: KoderaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKoderaOneBinding.inflate(inflater, container, false)

        koderaOneAdapter = KoderaOneAdapter()
        koderaOneAdapter.notifyDataSetChanged(list)
        binding.recyclerViewKoderaOne.adapter = koderaOneAdapter

        addData()
        addData()
        addData()
        addData()

        return binding.root
    }

    private fun addData() {
        val koderaOneData = KoderaOneData(list.count(),list.count().toString())
        koderaOneAdapter.notifyDataSetAdd(koderaOneData)
    }


}