package com.yazaki_groupcom.app.test

import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.base.BaseButton
import com.yazaki_groupcom.app.databinding.ActivityTestButtonBinding

class TestButtonActivity : BaseActivity()  {
    companion object {
        const val TAG: String = "TestButtonActivity"
    }
    private lateinit var binding: ActivityTestButtonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testButton2.setOnClickListener {

            binding.testButton2.isTouched = !binding.testButton2.isTouched
            //Log.e(TAG, "onCreate: binding.testButton2.isTouched:"+binding.testButton2.isTouched )
            binding.testButton2.changeColorByState(BaseButton.Companion.ButtonState.MULTIPLE.state)
        }
        binding.testButton3.setOnClickListener {
            binding.testButton3.changeColorByState(BaseButton.Companion.ButtonState.NORMAL.state)
        }
    }
}