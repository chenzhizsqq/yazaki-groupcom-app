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
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.databinding.FragmentKomaxTwoBinding

class KomaxTwoFragment() : Fragment() {

    companion object {
        const val TAG: String = "KomaxTwoFragment"
        fun newInstance() = KomaxTwoFragment()
    }

    private lateinit var binding: FragmentKomaxTwoBinding

    //与MainActivity共同的ViewModel
    val sharedVM: KomaxViewModel by activityViewModels()

    private lateinit var editTextArray : ArrayList<EditText>

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

        editTextArray = ArrayList<EditText>()
        editTextArray.add(binding.etInfo)
        editTextArray.add(binding.etInfo2)
        editTextArray.add(binding.etNumber)
        editTextArray.add(binding.etNumber2)

        editTextArray.forEach {
            it.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    // EditText获取了焦点，正在编辑中

                } else {
                    // EditText失去了焦点，不在编辑中

                    checkDataShowRet()
                }
            }

            it.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken, 0)

                    // 在这里处理enter键触碰事件

                    checkDataShowRet()
                    true
                } else {
                    false
                }
            }
        }

        sharedVM.komaxTwoData.observe(requireActivity()) {
            binding.terminalNumber1.text = it.terminal_number_1
            binding.skinSize1.text = it.skin_size_1
            binding.applicator1.text = it.applicator_1
            binding.partsNumber1.text = it.parts_number_1

            binding.terminalNumber2.text = it.terminal_number_2
            binding.skinSize2.text = it.skin_size_2
            binding.applicator2.text = it.applicator_2
            binding.partsNumber2.text = it.parts_number_2
        }

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


//        binding.etInfo.setOnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                // EditText获取了焦点，正在编辑中
//            } else {
//                // EditText失去了焦点，不在编辑中
//                Log.e(TAG, "!!!: binding.etInfo setOnFocusChangeListener", )
//
//                checkDataShowRet()
//            }
//        }
//
//        binding.etNumber.setOnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                // EditText获取了焦点，正在编辑中
//            } else {
//                // EditText失去了焦点，不在编辑中
//                Log.e(TAG, "!!!: binding.etNumber setOnFocusChangeListener", )
//                checkDataShowRet()
//            }
//        }
//
//
//        binding.etNumber.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                val imm =
//                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(binding.etNumber.windowToken, 0)
//
//                // 在这里处理enter键触碰事件
//                Log.e(TAG, "!!!: binding.etNumber setOnEditorActionListener 2222", )
//
//                checkDataShowRet()
//                true
//            } else {
//                false
//            }
//        }

        return binding.root
    }

    private fun checkDataShowRet() {
        val etInfoStr = binding.etInfo.text.toString()
        val etNumberStr = binding.etNumber.text.toString()
        val etInfoStr2 = binding.etInfo2.text.toString()
        val etNumberStr2 = binding.etNumber2.text.toString()
        if (etInfoStr.length >= 3 && etNumberStr.length >= 3 && etInfoStr2.length >= 3 && etNumberStr2.length >= 3) {
            val ret = checkDataResult(
                etInfoStr,
                etNumberStr,
                etInfoStr2,
                etNumberStr2)
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

    private fun checkDataResult(
        etInfoStr: String,
        etNumberStr: String,
        etInfoStr2: String,
        etNumberStr2: String,
    ): Boolean {
        if (etInfoStr.substring(0, 3) != "711") {
            return false
        }
        if (etNumberStr.substring(0, 3) != "230") {
            return false
        }
        if (etInfoStr2.substring(0, 3) != "711") {
            return false
        }
        if (etNumberStr2.substring(0, 3) != "P-1") {
            return false
        }
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}