package com.yazaki_groupcom.app.ui.kodera

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        binding.tvResult.setOnClickListener {
            sharedVM.idFragment.value = 3
        }

        sharedVM.scanDataText.value = ""

        sharedVM.scanDataText.observe(viewLifecycleOwner) {
            Log.e(TAG, " !!! QR:$it ")
            val str = it
            if (str.isNotEmpty()){
                if (checkQR(str)){
                    binding.tvResult.text = "OK"
                    binding.etInfo.setText(str.substring(1, 12))
                    binding.etNumber.setText(str.substring(24, 36))

                    Handler(Looper.getMainLooper()).postDelayed({
                        sharedVM.idFragment.value = 3
                    }, 1000) // 1000表示延时1秒钟
                }else{
                    binding.tvResult.text = "NG"
                    if (str.length > 37) {
                        binding.etInfo.setText(str.substring(1, 12))
                        binding.etNumber.setText(str.substring(24, 36))
                    }
                }
            }
        }

        return binding.root
    }

    private fun checkQR(str: String):Boolean {
        //QR:P1801R706930Z;17Q300030TO04232060059Z;23V88516D20230206Z;6Y Z;3Y001Z;52P1R7Z;53P069Z;54P30Z;11BR
        if (str.length > 37) {
            if (str.substring(1, 4) != "180"){
                return false
            }
            return str.substring(17, 21) == "3000"
        } else {
            return false
        }
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}