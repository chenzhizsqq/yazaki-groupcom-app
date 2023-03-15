package com.yazaki_groupcom.app.ui.komax

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KomaxViewModel  : ViewModel() {

    val idFragment = MutableLiveData(1)

    val isCheckOk = MutableLiveData(false)

    //KoderaOneFragment中，端子显示的类型
    val strDuanzi = MutableLiveData("シース剥ぎ寸法")

    //ScanDataText
    val scanDataText = MutableLiveData("")

    //KomaxTwoData
    val komaxTwoData = MutableLiveData(KomaxTwoData(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
    ))

}