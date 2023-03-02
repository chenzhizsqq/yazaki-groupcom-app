package com.yazaki_groupcom.app.ui.kodera

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.databinding.FragmentKoderaMainBinding

class KoderaMainFragment : Fragment() {

    companion object {
        fun newInstance() = KoderaMainFragment()
    }

    private lateinit var binding: FragmentKoderaMainBinding

    //与MainActivity共同的ViewModel
    val sharedVM: KoderaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKoderaMainBinding.inflate(inflater, container, false)
        binding.btTest.setOnClickListener {
            sharedVM.idFragment.value = "1"
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}