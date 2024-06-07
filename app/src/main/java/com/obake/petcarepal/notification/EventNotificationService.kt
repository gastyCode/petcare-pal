package com.obake.petcarepal.notification

import android.content.Context

class EventNotificationService(context: Context): NotificationService(context) {
    companion object {
        const val CALENDAR_CHANNEL_ID = "calendar_channel"
    }
}