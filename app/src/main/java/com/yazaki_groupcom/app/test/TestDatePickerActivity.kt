package com.yazaki_groupcom.app.test

import android.app.DatePickerDialog
import android.os.Bundle
import com.yazaki_groupcom.app.base.BaseActivity
import com.yazaki_groupcom.app.databinding.ActivityTestDatePickerBinding
import java.util.*


class TestDatePickerActivity : BaseActivity() {
    companion object {
        const val TAG: String = "TestDatePickerActivity"
    }

    private lateinit var binding: ActivityTestDatePickerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestDatePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //"点击选择日期"点击后，显示日期
        binding.TestDatePickerActivity.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // do something with the selected date
                    binding.selectedYear.text = selectedYear.toString()
                    binding.selectedMonth.text = selectedMonth.toString()
                    binding.selectedDay.text = selectedDay.toString()
                }, year, month, day)

            datePickerDialog.show()
        }
    }
}