package com.obake.petcarepal.notification

import android.content.Context


class ActivityNotificationService(context: Context): NotificationService(context) {
    companion object {
        const val ACTIVITY_CHANNEL_ID = "activity_channel"
    }
}