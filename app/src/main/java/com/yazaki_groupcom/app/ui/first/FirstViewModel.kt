package com.yazaki_groupcom.app.ui.first

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {

    //現在loading状態
    val isLoading = MutableLiveData(false)
}