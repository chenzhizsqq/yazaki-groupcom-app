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
import com.yazaki_groupcom.app.base.BaseButton
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

        // 生成条形码
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap("1234567890", BarcodeFormat.CODE_128, 1400, 300)
        binding.ivTest1.setImageBitmap(bitmap)
        binding.ivTest2.setImageBitmap(bitmap)
        binding.ivTest3.setImageBitmap(bitmap)
        binding.ivTest4.setImageBitmap(bitmap)

        //按下"条形码"按钮后，去查看页面 2
        binding.ivTest1.setOnClickListener {
            sharedVM.idFragment.value = 3
        }
        binding.ivTest2.setOnClickListener {
            sharedVM.idFragment.value = 3
        }
        binding.ivTest3.setOnClickListener {
            sharedVM.idFragment.value = 3
        }
        binding.ivTest4.setOnClickListener {
            sharedVM.idFragment.value = 3
        }

        //KoderaOneFragment中，端子显示的类型
        sharedVM.strDuanzi.observeForever {
            binding.duanzi1.text = it
            binding.duanzi2.text = it
            binding.duanzi3.text = it
            binding.duanzi4.text = it
        }

        //按下"检查"按钮后，去检查页面 3
        binding.btCheck1.setOnClickListener {
            sharedVM.idFragment.value = 2
        }
        binding.btCheck2.setOnClickListener {
            sharedVM.idFragment.value = 2
        }
        binding.btCheck3.setOnClickListener {
            sharedVM.idFragment.value = 2
        }
        binding.btCheck4.setOnClickListener {
            sharedVM.idFragment.value = 2
        }

        sharedVM.isCheckOk.observeForever {
            if (it){
                binding.btCheckRes.changeColorByState(BaseButton.Companion.ButtonState.NORMAL.state)
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}