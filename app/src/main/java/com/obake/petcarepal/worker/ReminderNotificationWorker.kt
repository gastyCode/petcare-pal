package com.obake.petcarepal.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderNotificationWorker(private val context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        val message = inputData.getString("message")

        NotificationHandler().createReminderNotification(context, message ?: "Reminder")
        return Result.success()
    }
}
