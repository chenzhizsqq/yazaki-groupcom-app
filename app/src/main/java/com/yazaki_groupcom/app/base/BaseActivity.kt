package com.yazaki_groupcom.app.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.FirstActivity
import com.yazaki_groupcom.app.Tools

/**
 * ベースアクティビティ
 * アクティビティ共通処理をここで処理する。
 */
open class BaseActivity : AppCompatActivity()
{

    companion object {
        const val TAG: String = "BaseActivity"
    }

    //カウントダウン スリープ- 倒数器sleep
    private  var countDownTimerSleep: CountDownTimer? = null

    //カウントダウン ログアウトに移行- 倒数器logout
    private  var countDownTimerLogout: CountDownTimer? = null

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

        //ログアウト time リセット 120分間
        resetLogoutTime()
    }

    //スリープ time リセット
    open fun resetSleepTime() {
        countDownTimerSleep?.cancel()
        val timeToSleepSS = Config.timeToSleep * 60
        countDownTimerSleepSetting(timeToSleepSS.toLong())
    }

    //ログアウト time リセット
    open fun resetLogoutTime() {
        countDownTimerLogout?.cancel()
        val countDownTimerLogout = Config.timeToLogout * 60
        countDownTimerLogoutSetting(countDownTimerLogout.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()

        countDownTimerSleep?.cancel()

        countDownTimerLogout?.cancel()
    }

    /**
     * Network判断
     * @return
     */
    open fun isNetworkConnected(): Boolean {
        return Tools.isNetworkConnected
    }

    //カウントダウン スリープ setting - 倒数时间的设定
    private fun countDownTimerSleepSetting(ss: Long) {
        countDownTimerSleep = object : CountDownTimer(ss * 1000, 300) {

            //倒数完的处理
            override fun onFinish() {
                Log.e(TAG, "countDownTimerSleepSetting onFinish(): ")
                //スリープ状態に移行
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }

            //倒数中的状况
            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / 1000 / 60 / 60
                val minute = millisUntilFinished / 1000 / 60 % 60
                val second = millisUntilFinished / 1000 % 60
                //Log.e(TAG, "countDownTimerSleepSetting onTick: 倒计时"+hour+"小时"+minute+"分"+second+"秒", )

            }
        }.start()
    }

    //カウントダウン logout setting - 倒数时间的设定
    private fun countDownTimerLogoutSetting(ss: Long) {
        countDownTimerLogout = object : CountDownTimer(ss * 1000, 300) {

            //倒数完的处理
            override fun onFinish() {
                Log.e(TAG, "countDownTimerLogoutSetting onFinish(): ")
                //スリープ状態に移行
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                //または2時間（時間は要検討）未操作状態が継続した場合、”スリープ“から自動でログアウト
                val intent =
                    Intent(this@BaseActivity, FirstActivity::class.java)
                startActivity(intent)
                finish()
            }

            //倒数中的状况
            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / 1000 / 60 / 60
                val minute = millisUntilFinished / 1000 / 60 % 60
                val second = millisUntilFinished / 1000 % 60
                //Log.e(TAG, "countDownTimerLogoutSetting onTick: 倒计时"+hour+"小时"+minute+"分"+second+"秒", )

            }
        }.start()
    }
}