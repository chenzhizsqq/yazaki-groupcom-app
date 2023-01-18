package com.yazaki_groupcom.app.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.YazakiApp

/**
 * ベースアクティビティ
 * アクティビティ共通処理をここで処理する。
 */
open class BaseActivity : AppCompatActivity()
{
    //倒数器
    private lateinit var countDownTimer: CountDownTimer

    //最后剩余时间 秒
    private var lastRemainingTime_ss: Int = 0

    companion object {
        const val TAG: String = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //設定画面を開いたままにする
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 縦画面
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        // タイトルバー非表示
        supportActionBar?.hide()

        //スリープ時間を設定　30分間
        resetSleepTime()
    }

    //睡眠時間を数え直す
    open fun resetSleepTime() {
        val timeSetting_ss = YazakiApp.sharedPreferences.getInt("lock_use_time", 30) * 60
        countDownTimerMM(timeSetting_ss.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()

        countDownTimer.onFinish()
        countDownTimer.cancel()
        YazakiApp.sharedPrePut(Config.remaining_time_ss, lastRemainingTime_ss)
        Log.d(
            TAG,
            "onDestroy: remaining_time" + YazakiApp.sharedPreferences.getInt(
                Config.remaining_time_ss,
                0
            )
        )
    }

    /**
     * Network判断
     * @return
     */
    open fun isNetworkConnected(): Boolean {
        return Tools.isNetworkConnected
    }

    //倒数时间的设定
    fun countDownTimerMM(ss: Long) {
        countDownTimer = object : CountDownTimer(ss * 1000, 300) {

            //倒数完的处理
            override fun onFinish() {
                Log.e(TAG, "countDownTimerSS onFinish(): ")

                YazakiApp.sharedPrePut(Config.remaining_time_ss, 0)

                //スリープ状態に移行
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }

            //倒数中的状况
            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / 1000 / 60 / 60
                val minute = millisUntilFinished / 1000 / 60 % 60
                val second = millisUntilFinished / 1000 % 60
                Log.e(TAG, "onTick: 倒计时"+hour+"小时"+minute+"分"+second+"秒", )
                lastRemainingTime_ss = (millisUntilFinished / 1000).toInt()

            }
        }.start()
    }
}