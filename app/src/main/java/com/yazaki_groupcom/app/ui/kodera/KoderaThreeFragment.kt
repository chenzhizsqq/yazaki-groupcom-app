package com.yazaki_groupcom.app.ui.kodera

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.databinding.FragmentKoderaThreeBinding

class KoderaThreeFragment : Fragment() {

    companion object {
        const val TAG: String = "KoderaThreeFragment"
        fun newInstance() = KoderaThreeFragment()
    }

    private lateinit var binding: FragmentKoderaThreeBinding

    //与MainActivity共同的ViewModel
    val sharedVM: KoderaViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(KoderaTwoFragment.TAG, "onCreateView: 333", )
        binding = FragmentKoderaThreeBinding.inflate(inflater, container, false)

        binding.tvTitle.setOnClickListener {
            sharedVM.idFragment.value = "1"
        }

        return binding.root
    }
}