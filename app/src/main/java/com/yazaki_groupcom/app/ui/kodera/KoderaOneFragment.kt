package com.yazaki_groupcom.app.ui.kodera

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yazaki_groupcom.app.databinding.FragmentKoderaOneBinding

class KoderaOneFragment : Fragment() {

    companion object {
        const val TAG: String = "KoderaOneFragment"
        fun newInstance() = KoderaOneFragment()
    }

    private lateinit var binding: FragmentKoderaOneBinding

    //与MainActivity共同的ViewModel
    val sharedVM: KoderaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG, "onCreateView: 111", )
        binding = FragmentKoderaOneBinding.inflate(inflater, container, false)
        binding.tvTitle.setOnClickListener {
            sharedVM.idFragment.value = "2"
        }
        binding.btCheck1.setOnClickListener {
            sharedVM.idFragment.value = "2"
        }
        binding.btCheck2.setOnClickListener {
            sharedVM.idFragment.value = "2"
        }
        binding.btCheck3.setOnClickListener {
            sharedVM.idFragment.value = "2"
        }
        binding.btCheck4.setOnClickListener {
            sharedVM.idFragment.value = "2"
        }

        // 生成条形码
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap("1234567890", BarcodeFormat.CODE_128, 1400, 300)
        binding.ivTest1.setImageBitmap(bitmap)
        binding.ivTest2.setImageBitmap(bitmap)
        binding.ivTest3.setImageBitmap(bitmap)
        binding.ivTest4.setImageBitmap(bitmap)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}