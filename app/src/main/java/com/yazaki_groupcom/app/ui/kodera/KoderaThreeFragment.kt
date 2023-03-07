package com.yazaki_groupcom.app.ui.kodera

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yazaki_groupcom.app.R
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
            sharedVM.idFragment.value = 1
        }

        binding.etCheck1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 不需要实现
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // 在这里判断EditText是否已经完成输入
                if (s.toString().trim().isNotEmpty()) {

                    if (Integer.parseInt(s.toString()) in 330..340)
                    {
                        changeTextView(binding.tvCheckResult1, "OK", R.drawable.bg_layout_black_green)
                    }else{
                        changeTextView(binding.tvCheckResult1, "NG", R.drawable.bg_layout_black_red)
                    }
                }

                CheckAllOk()
            }
        })

        binding.etCheck2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 不需要实现
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                // 在这里判断EditText是否已经完成输入
                if (s.toString().trim().isNotEmpty()) {

                    if (Integer.parseInt(s.toString()) in 21..29)
                    {
                        changeTextView(binding.tvCheckResult2, "OK", R.drawable.bg_layout_black_green)
                    }else{
                        changeTextView(binding.tvCheckResult2, "NG", R.drawable.bg_layout_black_red)
                    }
                }

                CheckAllOk()
            }
        })

        binding.etCheck3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 不需要实现
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // 在这里判断EditText是否已经完成输入
                if (s.toString().trim().isNotEmpty()) {

                    if (Integer.parseInt(s.toString()) in 21..29)
                    {
                        changeTextView(binding.tvCheckResult3, "OK", R.drawable.bg_layout_black_green)
                    }else{
                        changeTextView(binding.tvCheckResult3, "NG", R.drawable.bg_layout_black_red)
                    }
                }

                CheckAllOk()
            }
        })

        return binding.root
    }

    private fun changeTextView(
        textView: TextView,
        text: String,
        resId: Int
    ) {
        textView.text = text
        textView.setBackgroundResource(resId)
    }

    private fun CheckAllOk(){

        if (binding.tvCheckResult1.text.equals("OK")
            && binding.tvCheckResult2.text.equals("OK")
            && binding.tvCheckResult3.text.equals("OK")
        )
        {
            Handler(Looper.getMainLooper()).postDelayed({
                sharedVM.idFragment.value = 1
                sharedVM.isCheckOk.value = true
            }, 1000) // 1000表示延时1秒钟
        }
    }
}