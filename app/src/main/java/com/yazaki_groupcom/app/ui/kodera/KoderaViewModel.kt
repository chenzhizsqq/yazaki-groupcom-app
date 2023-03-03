package com.yazaki_groupcom.app.ui.kodera

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KoderaViewModel  : ViewModel() {

    val idFragment = MutableLiveData(1)

    val isCheckOk = MutableLiveData(false)

}