package com.obake.petcarepal

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.obake.petcarepal.data.model.Pet
import com.obake.petcarepal.notification.ActivityNotificationService
import com.obake.petcarepal.notification.EventNotificationService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("pet", Pet::class.java)
        } else {
            intent.getParcelableExtra("pet") as? Pet
        }

        createNotificationChannels()
        requestPermissions()

        setContent {
            Application(context = applicationContext, pet = pet)
        }
    }

    private fun createNotificationChannels() {
        val activityChannel = NotificationChannel(ActivityNotificationService.ACTIVITY_CHANNEL_ID, applicationContext.getString(R.string.activity_channel_name), NotificationManager.IMPORTANCE_HIGH)
        activityChannel.description = applicationContext.getString(R.string.activity_channel_desc)

        val calendarChannel = NotificationChannel(EventNotificationService.CALENDAR_CHANNEL_ID, applicationContext.getString(R.string.calendar_channel_name), NotificationManager.IMPORTANCE_HIGH)
        activityChannel.description = applicationContext.getString(R.string.calendar_channel_desc)

        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(activityChannel)
        notificationManager.createNotificationChannel(calendarChannel)
    }

    private fun requestPermissions() {
        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}

        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            if (!shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }
}
