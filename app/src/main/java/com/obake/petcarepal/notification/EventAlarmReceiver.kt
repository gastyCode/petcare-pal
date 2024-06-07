package com.obake.petcarepal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class EventAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val eventNotificationService = EventNotificationService(context)
        val title = intent?.getStringExtra("title") ?: return
        val message = intent.getStringExtra("message") ?: return
        val id = intent.getIntExtra("id", 0)

        eventNotificationService.showNotification(title, message, id)
    }
}