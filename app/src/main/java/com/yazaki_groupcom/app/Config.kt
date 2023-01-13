package com.yazaki_groupcom.app

class Config {
    companion object {
        const val ApiUrl = "https://localhost/"

        // 連打判断間隔msec
        const val DOUBLE_CLICK_INTERVAL = 500L

        const val AppUser = "AppUser"
        const val Device = "android"

        //初回登録Frag key
        const val FragKey = "selectFragKey"

        //LOCATION KEY
        const val REQUEST_CODE_LOCATION_KEY = 10008
    }

}