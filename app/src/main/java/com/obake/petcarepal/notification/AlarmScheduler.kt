package com.obake.petcarepal.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.content.getSystemService
import com.obake.petcarepal.R
import java.util.Calendar

class AlarmScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java) as AlarmManager

    fun scheduleActivity(message: String, time: Long, id: Int) {
        val intent = Intent(context, ActivityAlarmReceiver::class.java).apply {
            putExtra("title", context.getString(R.string.planned_activity))
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

    fun scheduleEvent(message: String, time: Long, id: Int) {
        val intent = Intent(context, EventAlarmReceiver::class.java).apply {
            putExtra("title", context.getString(R.string.planned_event))
            putExtra("message", message)
            putExtra("id", id)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time,
            AlarmManager.INTERVAL_DAY,
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
                Intent(context, EventAlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}