package com.yazaki_groupcom.app.ui.kodera

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.databinding.FragmentKoderaOneBinding
import com.yazaki_groupcom.app.databinding.FragmentKoderaTwoBinding

class KoderaTwoFragment : Fragment() {

    companion object {
        const val TAG: String = "KoderaTwoFragment"
        fun newInstance() = KoderaTwoFragment()
    }

    private lateinit var binding: FragmentKoderaTwoBinding

    //与MainActivity共同的ViewModel
    val sharedVM: KoderaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG, "onCreateView: 222", )
        binding = FragmentKoderaTwoBinding.inflate(inflater, container, false)
        binding.tvTitle.setOnClickListener {
            sharedVM.idFragment.value = "1"
        }
        binding.tvResult.setOnClickListener {
            sharedVM.idFragment.value = "3"
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}