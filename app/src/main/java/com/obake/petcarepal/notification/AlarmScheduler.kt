package com.obake.petcarepal.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class AlarmScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java) as AlarmManager

    fun scheduleActivity(message: String, time: Long, id: Int) {
        val intent = Intent(context, ActivityAlarmReceiver::class.java).apply {
            putExtra("message", message)
            putExtra("id", id)
        }

        var notificationTime = time
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        if (calendar.time < Calendar.getInstance().time) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            notificationTime = calendar.timeInMillis
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            notificationTime,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleCalendar(message: String, time: Long, id: Int) {
        val intent = Intent(context, CalendarAlarmReceiver::class.java).apply {
            putExtra("message", message)
            putExtra("id", id)
        }
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time,
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancelActivity(id: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(context, ActivityAlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancelCalendar(id: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(context, CalendarAlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}