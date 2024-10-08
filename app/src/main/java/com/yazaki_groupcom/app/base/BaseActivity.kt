package com.yazaki_groupcom.app.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.Tools
import com.yazaki_groupcom.app.enum.ShareKey
import com.yazaki_groupcom.app.ui.first.FirstActivity
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * ベースアクティビティ
 * アクティビティ共通処理をここで処理する。
 */
open class BaseActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "BaseActivity"
    }

    //カウントダウン スリープ- 倒数器sleep
    private var countDownTimerSleep: CountDownTimer? = null

    //カウントダウン ログアウトに移行- 倒数器logout
    private var countDownTimerLogout: CountDownTimer? = null

    //現在使用中のユーザー名
    val currentUserName = Tools.sharedPreGetString(ShareKey.CurrentUserName.key).take(8)

    // TODO: 是否设定倒数时间
    private val isSettingCountTimeTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 縦画面
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        // タイトルバー非表示
        supportActionBar?.hide()

        if (isSettingCountTimeTime) {
            //設定画面を開いたままにする  スリープ状態はなし sleep off
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            //スリープ時間を設定　30分間
            resetSleepTime()
            //ログアウト time リセット 120分間
            resetLogoutTime()
        }
    }

    //按左下角的 "三角"按钮后，触发的返回函数
    override fun onBackPressed() {
        super.onBackPressed()
    }

    //整个Activity()终结后，调用的函数
    override fun finish() {
        intent.putExtra(Config.LastActivityName, this.javaClass.name)
        super.finish()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (isSettingCountTimeTime) {
            if (ev != null) {
                if (ev.action == ACTION_DOWN) {
                    //在这里处理触屏事件: ACTION_DOWN。重新设定时间
                    resetSleepTime()
                    resetLogoutTime()
                }
            }
        }
        // TODO: 在这里处理触屏事件
        return super.dispatchTouchEvent(ev)
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
        countDownTimerSleep = object : CountDownTimer(ss * 1000, 30000) {

            //倒数完的处理
            override fun onFinish() {
                Log.i(TAG, "countDownTimerSleepSetting onFinish(): ")
                //スリープ状態に移行
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }

            //倒数中的状况
            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / 1000 / 60 / 60
                val minute = millisUntilFinished / 1000 / 60 % 60
                val second = millisUntilFinished / 1000 % 60
                //Log.i(TAG, "countDownTimerSleepSetting onTick: 倒计时"+hour+"小时"+minute+"分"+second+"秒", )

            }
        }.start()
    }

    //カウントダウン logout setting - 倒数时间的设定
    private fun countDownTimerLogoutSetting(ss: Long) {
        countDownTimerLogout = object : CountDownTimer(ss * 1000, 30000) {

            //倒数完的处理
            override fun onFinish() {
                Log.i(TAG, "countDownTimerLogoutSetting onFinish(): ")
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
                //Log.i(TAG, "countDownTimerLogoutSetting onTick: 倒计时"+hour+"小时"+minute+"分"+second+"秒", )

            }
        }.start()
    }


    //有意外发生时的对应线程
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "throwable: $throwable")
    }
}