package com.obake.petcarepal.data

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.obake.petcarepal.R
import kotlin.random.Random


class NotificationService(private val context: Context) {
    private val notificationManager=context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification(description: String){
        val notification= NotificationCompat.Builder(context,"petcare_notification")
            .setContentTitle(R.string.app_name.toString())
            .setContentText(description)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}