package com.yazaki_groupcom.app.ui.processManage

//ProcessDataList
data class ProcessData(
    var title: String,
    var data: String,
)

data class ProcessDataList(
    var titleArray: ArrayList<ProcessData>,
)
