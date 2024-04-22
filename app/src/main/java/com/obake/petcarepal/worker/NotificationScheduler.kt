package com.obake.petcarepal.worker

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

class NotificationScheduler(private val context: Context) {
    fun schedule(timeInMillis: Long, message: String, id: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        val data: Data = Data.Builder()
            .putString("message", message)
            .build()

        val notificationRequest = PeriodicWorkRequestBuilder<ReminderNotificationWorker>(24, TimeUnit.HOURS)
            .addTag(id.toString())
            .setInitialDelay(calendar.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork("reminder_notification", ExistingPeriodicWorkPolicy.UPDATE, notificationRequest)
    }

    fun cancel(id: Long) {
        WorkManager.getInstance(context).cancelAllWorkByTag(id.toString())
    }
}