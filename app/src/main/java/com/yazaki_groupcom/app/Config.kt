package com.yazaki_groupcom.app

class Config {
    companion object {
        const val ApiUrl = "https://localhost/"

        // 連打判断間隔msec
        const val DOUBLE_CLICK_INTERVAL = 500L

        const val AppUser = "AppUser"
        const val Device = "android"

        //最后运行的Activity
        const val LastActivityName = "LastActivityName"

        //LOCATION KEY
        const val REQUEST_CODE_LOCATION_KEY = 10008

        //30分間（分数は要検討）操作が無い場合、スリープ状態に移行。
        const val timeToSleep = 30

        //2時間（分数は要検討）操作が無い場合、ログアウトに移行。
        const val timeToLogout = 120

        const val databaseName = "my_database"

        //默认按键背景颜色
        const val buttonBgColor = R.color.purple_500

        //是否测试状态
        const val isCheckMode = true
    }

}