package com.obake.petcarepal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CalendarAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val calendarNotificationService = CalendarNotificationService(context)
        val message = intent?.getStringExtra("message") ?: return
        val id = intent.getIntExtra("id", 0)

        calendarNotificationService.showNotification(message, id)
    }
}