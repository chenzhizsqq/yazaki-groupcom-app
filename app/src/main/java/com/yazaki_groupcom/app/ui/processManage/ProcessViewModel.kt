package com.yazaki_groupcom.app.ui.processManage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProcessViewModel  : ViewModel() {

    //已经扫码了
    val isScanned = MutableLiveData(false)

}