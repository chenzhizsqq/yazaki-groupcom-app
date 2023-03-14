package com.yazaki_groupcom.app.ui.komax

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.databinding.FragmentKomaxTwoBinding

class KomaxTwoFragment : Fragment() {

    companion object {
        const val TAG: String = "KomaxTwoFragment"
        fun newInstance() = KomaxTwoFragment()
    }

    private lateinit var binding: FragmentKomaxTwoBinding

    //与MainActivity共同的ViewModel
    val sharedVM: KomaxViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG, "onCreateView: 222", )
        binding = FragmentKomaxTwoBinding.inflate(inflater, container, false)
        if (Config.isCheckMode) {
            binding.tvResult.setOnClickListener {
                sharedVM.idFragment.value = 3
            }
        }

        binding.type.text = Tools.sharedPreGetString("KomaxOneAdapter_type")
        binding.size.text = Tools.sharedPreGetString("KomaxOneAdapter_size")
        binding.color.text = Tools.sharedPreGetString("KomaxOneAdapter_color")
        binding.longSize.text = Tools.sharedPreGetString("KomaxOneAdapter_longSize")

//        sharedVM.scanDataText.value = ""
//        sharedVM.scanDataText.observe(viewLifecycleOwner) {
//            Log.e(TAG, " !!! QR:$it ")
//            val str = it
//            if (str.isNotEmpty()){
//                if (checkQR(str)){
//                    binding.tvResult.text = "OK"
//                    binding.etInfo.setText(str.substring(1, 12))
//                    binding.etNumber.setText(str.substring(24, 36))
//
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        sharedVM.idFragment.value = 3
//                    }, 1000) // 1000表示延时1秒钟
//                }else{
//                    binding.tvResult.text = "NG"
//                    if (str.length > 37) {
//                        binding.etInfo.setText(str.substring(1, 12))
//                        binding.etNumber.setText(str.substring(24, 36))
//                    }
//                }
//            }
//        }


        binding.etInfo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // EditText获取了焦点，正在编辑中
            } else {
                // EditText失去了焦点，不在编辑中
                Log.e(TAG, "!!!: binding.etInfo setOnFocusChangeListener", )

                checkDataShowRet()
            }
        }

        binding.etNumber.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // EditText获取了焦点，正在编辑中
            } else {
                // EditText失去了焦点，不在编辑中
                Log.e(TAG, "!!!: binding.etNumber setOnFocusChangeListener", )
                checkDataShowRet()
            }
        }


        binding.etNumber.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etNumber.windowToken, 0)

                // 在这里处理enter键触碰事件
                Log.e(TAG, "!!!: binding.etNumber setOnEditorActionListener 2222", )

                checkDataShowRet()
                true
            } else {
                false
            }
        }

        return binding.root
    }

    private fun checkDataShowRet() {
        val etInfoStr = binding.etInfo.text.toString()
        val etNumberStr = binding.etNumber.text.toString()
        if (etInfoStr.length > 3 && etNumberStr.length > 3) {
            val ret = checkData(etInfoStr, etNumberStr)
            if (ret) {
                binding.tvResult.text = "OK"
                Handler(Looper.getMainLooper()).postDelayed({
                    sharedVM.idFragment.value = 3
                }, 1000) // 1000表示延时1秒钟
            } else {
                binding.tvResult.text = "NG"
            }
        }else{
            binding.tvResult.text = "ー"
        }
    }

    private fun checkData(
        etInfoStr: String,
        etNumberStr: String
    ): Boolean {
        if (etInfoStr.substring(0, 3) != "180") {
            Log.e(TAG, "!!!: binding.etInfo setOnFocusChangeListener okok")
            return false
        }
        if (etNumberStr.substring(0, 3) != "004") {
            return false
        }
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}