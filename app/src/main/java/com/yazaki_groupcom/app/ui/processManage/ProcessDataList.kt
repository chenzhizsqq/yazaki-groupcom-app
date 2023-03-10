package com.yazaki_groupcom.app.ui.processManage

//ProcessDataList
data class ProcessData(
    var title: String,
    var data: String,

    val info_date:String ,
    val info_jisai:String ,
    val info_zhishi:String ,
    val info_jinbu:String ,
)

data class ProcessDataList(
    var titleArray: ArrayList<ProcessData>,
)
