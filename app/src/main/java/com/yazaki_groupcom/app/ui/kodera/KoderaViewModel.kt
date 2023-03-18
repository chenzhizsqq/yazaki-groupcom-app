package com.yazaki_groupcom.app.ui.kodera

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KoderaViewModel  : ViewModel() {
    val gotoNext = MutableLiveData(false)

    val idFragment = MutableLiveData(2)

    //KoderaOneFragment中，端子显示的类型
    val strDuanzi = MutableLiveData("シース剥ぎ寸法")

    //ScanDataText
    val scanDataText = MutableLiveData("")

}