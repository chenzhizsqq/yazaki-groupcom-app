package com.yazaki_groupcom.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * スキャン後に取得されたデータ
 */
class BaseScanViewModel : ViewModel() {

    /**
     * "unitech.scanservice.data" の text
     */
    val dataText = MutableLiveData("")
}