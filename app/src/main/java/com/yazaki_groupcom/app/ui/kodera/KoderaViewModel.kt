package com.yazaki_groupcom.app.ui.kodera

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KoderaViewModel  : ViewModel() {

    val idFragment = MutableLiveData(1)

    val isCheckOk = MutableLiveData(false)

    val checkOverIdList = MutableLiveData(ArrayList<Int>())

    //KoderaOneFragment中，端子显示的类型
    val strDuanzi = MutableLiveData("シース剥ぎ寸法")

    //ScanDataText
    val scanDataText = MutableLiveData("")

    //KomaxTwoData
    val koderaEachData = MutableLiveData(
        KoderaEachData(
            -1,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            0,
            0,
            false
        )
    )

}