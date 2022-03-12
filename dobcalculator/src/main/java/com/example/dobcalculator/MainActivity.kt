package com.example.dobcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dobcalculator.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSelectedDate.text = "yyyy/MM/dd"
        binding.tvAgeInMinutes.text = "0"

        binding.btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val today : Calendar = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val dayOfMonth = today.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // 선택한 날짜 표시
                val selectedDate = "$selectedYear/${selectedMonth + 1}/$selectedDayOfMonth"
                binding.tvSelectedDate.text = selectedDate

                // 선택한 날로부터 오늘까지 경과 시간을 분으로 표시
                val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA)

                val selectedDateInMinutes = sdf.parse(selectedDate).time / 60000
                val currentDateInMinutes = sdf.parse(sdf.format(System.currentTimeMillis())).time / 60000

                val diff = (currentDateInMinutes - selectedDateInMinutes)

                // 분, 시간, 일
                binding.tvAgeInMinutes.text = diff.toString()
                binding.tvAgeInHour.text = (diff / 60).toString()
                binding.tvAgeInDay.text = (diff / (60 * 24)).toString()
            },
            year,
            month,
            dayOfMonth
        )
        // 선택 가능한 최대 날짜를 어제로 설정
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86_400_000
        dpd.show()

    }
}