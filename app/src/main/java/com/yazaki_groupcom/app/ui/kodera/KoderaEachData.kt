package com.yazaki_groupcom.app.ui.kodera

//KomaxTwoFragment的数据
data class KoderaEachData(
    var id: Int,
    var title: String,

    var facility: String,
    var amount: String,
    var cuttingAmount: String,
    var cuttingDate: String,

    var groupNumber1: String,
    var cerealNumber1: String,
    var variety1: String,
    var size1: String,
    var color1: String,
    var cuttingLineLength1: String,
    var dimensions: String,

    var amountState: Int,
    var variety1State: Int,
    var size1State: Int,
    var color1State: Int,
    var cuttingLineLength1State: Int,

    var isCheckOver : Boolean
)