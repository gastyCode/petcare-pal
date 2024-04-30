package com.obake.petcarepal.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.text.SimpleDateFormat
import java.util.Calendar

object DateHelper {
     fun millisToDate(milliseconds: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        val format = SimpleDateFormat("dd.MM.YYYY")
        return format.format(calendar.time).toString()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun timeStateToMillis(timePickerState: TimePickerState): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        calendar.set(Calendar.MINUTE, timePickerState.minute)
        return calendar.timeInMillis
    }
}