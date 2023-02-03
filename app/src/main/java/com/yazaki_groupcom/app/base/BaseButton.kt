package com.yazaki_groupcom.app.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.yazaki_groupcom.app.Config
import com.yazaki_groupcom.app.R


/**
 * 自定义圆角类按钮
 */
open class BaseButton : AppCompatButton {

    companion object {
        const val TAG: String = "BaseButton"

        enum class ButtonState(val state: Int) {
            NORMAL(1),      //有効ボタン1：押下可能なボタン
            MULTIPLE(2),    //有効ボタン2：複数の有効ボタンを差別化したい場合に使用
            INVALID(3),     //無効ボタン：押下不可能なボタン
        }
    }
    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)

    }

    private var attrs: AttributeSet? = null

    //是否已经被选中了
    var isTouched = false

    //按钮的样式
    private var buttonState = 0

    private fun initView(context: Context, attrs: AttributeSet?) {
        //获取自定义的属性值
        val typedBaseButton = context.obtainStyledAttributes(attrs, R.styleable.BaseButton)

        //按钮的样式
        buttonState =
            typedBaseButton.getInt(R.styleable.BaseButton_button_state, 0)
        //Log.e(TAG, "!!!initView: buttonState:$buttonState", )

        //是否已经被选中了
        isTouched = typedBaseButton.getBoolean(R.styleable.BaseButton_is_touched, false)

        this.attrs = attrs

        changeColorByState(buttonState)

    }

    fun changeColorByState(buttonState: Int) {

//         Log.e(TAG, "changeState: name:"+this.text )
//         Log.e(TAG, "changeState: buttonState:$_buttonState", )

        when (buttonState) {
            ButtonState.NORMAL.state -> {
                Log.e(TAG, "changeColorByState: 1")

                val textColor = Color.parseColor("#FFFFFFFF")
                val bgColor = Config.buttonBgColor

                changeButtonColor(textColor, bgColor)
            }
            ButtonState.MULTIPLE.state -> {

                Log.e(TAG, "changeColorByState: 2")
                if (isTouched){

                    val textColor = Color.BLACK
                    val bgColor = R.color.ic_app_icon_background

                    changeButtonColor(textColor, bgColor)
                }else{

                    val textColor = Color.BLACK
                    val bgColor = R.color.phone_button_color

                    changeButtonColor(textColor, bgColor)
                }

            }
            ButtonState.INVALID.state -> {

                Log.e(TAG, "changeColorByState: 3")

                val textColor = Color.BLACK
                val bgColor = R.color.silvery

                changeButtonColor(textColor, bgColor)
            }
            else ->{
                Log.e(TAG, "changeColorByState: else")
                changeButtonColor(null, null)
            }
        }
    }

    private fun changeButtonColor(textCol: Int?, bgCol: Int?) {
        //获取自定义的属性值
        val typedBaseButton = context.obtainStyledAttributes(attrs, R.styleable.BaseButton)

        if (textCol != null) {
            this.setTextColor(textCol)
        }

        //获取默认的颜色值 如果按钮没有设置颜色值 默认为这个颜色
        var default = bgCol?.let { ContextCompat.getColor(context, it) }
        if (bgCol==null){
            default = ContextCompat.getColor(context, Config.buttonBgColor)
        }
        //获取设置的背景色
        val bgColor = default?.let { typedBaseButton.getColor(R.styleable.BaseButton_bg_color, it) }
        //获取设置的圆角大小
        val buttonCorner =
            typedBaseButton.getDimensionPixelSize(R.styleable.BaseButton_bg_corner, 5)
        //生成圆角图片
        val bgcDrawable = GradientDrawable()
        //设置图片颜色
        if (bgColor != null) {
            bgcDrawable.setColor(bgColor)
        }
        //设置圆角大小
        bgcDrawable.cornerRadius = buttonCorner.toFloat()

        //生成一张半透明的灰色图片 #31000000为遮罩颜色 可自定义
        val bgcDrawable1 = GradientDrawable()
        bgcDrawable1.setColor(Color.parseColor("#31000000"))
        bgcDrawable1.cornerRadius = buttonCorner.toFloat()

        //生成一个图层叠加的图片 上面用灰色盖住 模拟变暗效果
        val arr = arrayOf(bgcDrawable, bgcDrawable1)
        val layerDrawable = LayerDrawable(arr)

        //设置点击后 变暗效果
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), layerDrawable)
        stateListDrawable.addState(intArrayOf(), bgcDrawable)

        background = stateListDrawable
        typedBaseButton.recycle()
    }

}