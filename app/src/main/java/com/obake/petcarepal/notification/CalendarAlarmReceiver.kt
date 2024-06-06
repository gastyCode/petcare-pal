package com.obake.petcarepal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CalendarAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val calendarNotificationService = CalendarNotificationService(context)
        val title = intent?.getStringExtra("title") ?: return
        val message = intent.getStringExtra("message") ?: return
        val id = intent.getIntExtra("id", 0)

        calendarNotificationService.showNotification(title, message, id)
    }
}