package com.obake.petcarepal

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
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

        setContent {
            var hasNotificationPermission by remember {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mutableStateOf(
                        ContextCompat.checkSelfPermission(
                            applicationContext,
                            android.Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    )
                } else {
                    mutableStateOf(true)
                }
            }

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    hasNotificationPermission = isGranted
                }
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

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
}
