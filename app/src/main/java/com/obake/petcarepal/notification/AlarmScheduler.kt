package com.obake.petcarepal.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.AlarmClock
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.getSystemService
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
    fun scheduleEvent(message: String, time: Long, id: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService<AlarmManager>()!!
            when {
                !alarmManager.canScheduleExactAlarms() -> {
                    context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                }
            }
        }

        val intent = Intent(context, CalendarAlarmReceiver::class.java).apply {
            putExtra("message", message)
            putExtra("id", id)
        }
        alarmManager.setExactAndAllowWhileIdle(
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

    fun cancelEvent(id: Int) {
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