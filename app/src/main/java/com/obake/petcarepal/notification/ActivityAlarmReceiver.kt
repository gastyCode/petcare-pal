package com.obake.petcarepal.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ActivityAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val activityNotificationService = ActivityNotificationService(context)
        val title = intent?.getStringExtra("title") ?: return
        val message = intent.getStringExtra("message") ?: return
        val id = intent.getIntExtra("id", 0)

        activityNotificationService.showNotification(title, message, id)
    }
}