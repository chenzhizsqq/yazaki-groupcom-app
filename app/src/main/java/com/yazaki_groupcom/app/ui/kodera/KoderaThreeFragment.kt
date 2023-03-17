package com.yazaki_groupcom.app.ui.kodera

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.databinding.FragmentKoderaThreeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KoderaThreeFragment : Fragment() {

    companion object {
        const val TAG: String = "KoderaThreeFragment"
        fun newInstance() = KoderaThreeFragment()
    }

    private lateinit var binding: FragmentKoderaThreeBinding

    //与MainActivity共同的ViewModel
    private val sharedVM: KoderaViewModel by activityViewModels()

    //能够去检查
    private var isCanBeCheck = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKoderaThreeBinding.inflate(inflater, container, false)

        isCanBeCheck = true
        changeTextView(binding.etCheck1, "", R.drawable.bg_layout_black)
        changeTextView(binding.etCheck2, "", R.drawable.bg_layout_black)
        changeTextView(binding.etCheck3, "", R.drawable.bg_layout_black)
        changeTextView(binding.tvCheckResult1, "", R.drawable.bg_layout_black)
        changeTextView(binding.tvCheckResult2, "", R.drawable.bg_layout_black)
        changeTextView(binding.tvCheckResult3, "", R.drawable.bg_layout_black)

        if (Config.isCheckMode){
            binding.tvTitle.setOnClickListener {
                isCanBeCheck = false
                lifecycleScope.launch {
                    delay(1000L) // 延迟1秒钟
                    sharedVM.idFragment.value = 1
                    sharedVM.isCheckOk.value = true
                }
            }
        }

        binding.etCheck1.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // EditText获取了焦点，正在编辑中
            } else {
                if (isCanBeCheck){
                    // EditText失去了焦点，不在编辑中
                    if (binding.etCheck1.text.toString() == "") {
                        changeTextView(binding.tvCheckResult1, "", R.drawable.bg_layout_black)
                    } else {
                        if (Integer.parseInt(binding.etCheck1.text.toString()) in 330..340) {
                            changeTextView(
                                binding.tvCheckResult1,
                                "OK",
                                R.drawable.bg_layout_black_green
                            )
                            checkAllOk()
                        } else {
                            changeTextView(binding.tvCheckResult1, "NG", R.drawable.bg_layout_black_red)
                            allChange2Default()
                        }
                    }
                }
            }
        }

        binding.etCheck2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // EditText获取了焦点，正在编辑中
            } else {
                // EditText失去了焦点，不在编辑中
                if (isCanBeCheck) {
                    if (binding.etCheck2.text.toString() == "") {
                        changeTextView(binding.tvCheckResult2, "", R.drawable.bg_layout_black)
                    } else {
                        if (Integer.parseInt(binding.etCheck2.text.toString()) in 21..29) {
                            changeTextView(
                                binding.tvCheckResult2,
                                "OK",
                                R.drawable.bg_layout_black_green
                            )
                            checkAllOk()
                        } else {
                            changeTextView(
                                binding.tvCheckResult2,
                                "NG",
                                R.drawable.bg_layout_black_red
                            )
                            allChange2Default()
                        }
                    }
                }
            }
        }

        binding.etCheck3.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // EditText获取了焦点，正在编辑中
            } else {
                // EditText失去了焦点，不在编辑中
                if (isCanBeCheck) {
                    if (binding.etCheck3.text.toString() == "") {
                        changeTextView(binding.tvCheckResult3, "", R.drawable.bg_layout_black)
                    } else {
                        if (Integer.parseInt(binding.etCheck3.text.toString()) in 21..29) {
                            changeTextView(
                                binding.tvCheckResult3,
                                "OK",
                                R.drawable.bg_layout_black_green
                            )
                            checkAllOk()
                        } else {
                            changeTextView(
                                binding.tvCheckResult3,
                                "NG",
                                R.drawable.bg_layout_black_red
                            )
                            allChange2Default()
                        }
                    }
                }
            }
        }

        binding.etCheck3.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etCheck3.windowToken, 0)

                // 在这里处理enter键触碰事件
                if (binding.etCheck3.text.toString() == "") {
                    changeTextView(binding.tvCheckResult3, "", R.drawable.bg_layout_black)
                } else {
                    if (Integer.parseInt(binding.etCheck3.text.toString()) in 21..29) {
                        changeTextView(
                            binding.tvCheckResult3,
                            "OK",
                            R.drawable.bg_layout_black_green
                        )
                        checkAllOk()
                    } else {
                        changeTextView(binding.tvCheckResult3, "NG", R.drawable.bg_layout_black_red)
                        allChange2Default()
                    }
                }
                true
            } else {
                false
            }
        }
        return binding.root
    }

    /**
     * すべてデフォルトに戻す
     */
    private fun allChange2Default() {
        lifecycleScope.launch {
            delay(1000L) // 延迟1秒钟
            changeTextView(binding.etCheck1, "", R.drawable.bg_layout_black)
            changeTextView(binding.etCheck2, "", R.drawable.bg_layout_black)
            changeTextView(binding.etCheck3, "", R.drawable.bg_layout_black)
            changeTextView(binding.tvCheckResult1, "", R.drawable.bg_layout_black)
            changeTextView(binding.tvCheckResult2, "", R.drawable.bg_layout_black)
            changeTextView(binding.tvCheckResult3, "", R.drawable.bg_layout_black)
        }
    }

    private fun changeTextView(
        textView: TextView,
        text: String,
        resId: Int
    ) {
        textView.text = text
        textView.setBackgroundResource(resId)
    }

    /**
     * 全部「判定」の結果確定
     */
    private fun checkAllOk() {
        if (binding.tvCheckResult1.text.equals("OK")
            && binding.tvCheckResult2.text.equals("OK")
            && binding.tvCheckResult3.text.equals("OK")
        ) {
            isCanBeCheck = false
            lifecycleScope.launch {
                delay(1000L) // 延迟1秒钟
                sharedVM.idFragment.value = 1
                sharedVM.isCheckOk.value = true
                sharedVM.koderaEachData.value?.id?.let { sharedVM.checkOverIdList.value?.add(it) }
            }
        }
    }
}