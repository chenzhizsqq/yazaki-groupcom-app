package com.yazaki_groupcom.app.ui.kodera

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yazaki_groupcom.app.R
import com.yazaki_groupcom.app.Tools
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
        //makeBarcodeEncoder()

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
                binding.btCheckRet1.changeColorByState(1)
            }else{
                binding.btCheckRet1.changeColorByState(3)
            }
        }

        //"检查"　"切断完了"　ボタンの設定
        clickButtonListen(binding.btCheck1 , binding.btCheckRet1)
        clickButtonListen(binding.btCheck2 , binding.btCheckRet2)
        clickButtonListen(binding.btCheck3 , binding.btCheckRet3)
        clickButtonListen(binding.btCheck4 , binding.btCheckRet4)

        return binding.root
    }

    /**
     * "检查"　"切断完了"　ボタンの設定
     */
    private fun clickButtonListen(
        checkBT: BaseButton,
        checkedBT: BaseButton
    ) {
        checkedBT.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(resources.getString(R.string.cut_off_title))
            builder.setMessage(resources.getString(R.string.cut_off_message))
            builder.setPositiveButton(resources.getString(R.string.cut_off_ok)) { dialog, which ->
                // 点击 OK 按钮的回调
                checkedBT.changeColorByState(3)
                checkBT.changeColorByState(3)

                //写入文件内容
                var fileContent = ""
                val csvStringBuilder = StringBuilder()
                csvStringBuilder.append("Name, Age, Gender\n")
                csvStringBuilder.append("John, 30, Male\n")
                csvStringBuilder.append("Jane, 25, Female\n")
                csvStringBuilder.append("Bob, 40, Male\n")
                fileContent = csvStringBuilder.toString()

                //用文字内容写成csv
                Tools.makeCsv(fileContent)
            }
            builder.setNegativeButton(resources.getString(R.string.cut_off_cancel)) { dialog, which ->
                // 点击 Cancel 按钮的回调
            }
            val dialog = builder.create()
            dialog.show()
        }
        if (sharedVM.isCheckOk.value == false) {
            checkedBT.changeColorByState(3)
        }
    }

    /**
     * 生成条形码
     */
    private fun makeBarcodeEncoder() {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap("1234567890", BarcodeFormat.CODE_128, 1400, 300)
        binding.ivTest1.setImageBitmap(bitmap)
        binding.ivTest2.setImageBitmap(bitmap)
        binding.ivTest3.setImageBitmap(bitmap)
        binding.ivTest4.setImageBitmap(bitmap)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}