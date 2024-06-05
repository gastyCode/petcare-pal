package com.obake.petcarepal

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.obake.petcarepal.data.model.Pet
import com.obake.petcarepal.notification.ActivityNotificationService
import com.obake.petcarepal.notification.CalendarNotificationService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var pet: Pet? = null
        pet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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

        val calendarChannel = NotificationChannel(CalendarNotificationService.CALENDAR_CHANNEL_ID, applicationContext.getString(R.string.calendar_channel_name), NotificationManager.IMPORTANCE_HIGH)
        activityChannel.description = applicationContext.getString(R.string.calendar_channel_desc)

        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(activityChannel)
        notificationManager.createNotificationChannel(calendarChannel)
    }

    private fun requestPermissions() {
        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}

        if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
            // permission granted
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)){
                // show rationale and then launch launcher to request permission
            } else {
                // first request or forever denied case
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }
}
