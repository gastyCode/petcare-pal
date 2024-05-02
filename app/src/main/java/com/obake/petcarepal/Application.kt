package com.obake.petcarepal

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.obake.petcarepal.data.ApplicationDatabase
import com.obake.petcarepal.data.Screen
import com.obake.petcarepal.ui.Navigation
import com.obake.petcarepal.ui.activities.ActivitiesScreen
import com.obake.petcarepal.ui.activities.ActivitiesViewModel
import com.obake.petcarepal.ui.addpet.AddPetScreen
import com.obake.petcarepal.ui.addpet.AddPetViewModel
import com.obake.petcarepal.ui.calendar.CalendarScreen
import com.obake.petcarepal.ui.overview.OverviewScreen
import com.obake.petcarepal.ui.overview.OverviewViewModel
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import com.obake.petcarepal.ui.tips.TipsScreen
import com.obake.petcarepal.ui.tips.TipsViewModel
import com.obake.petcarepal.util.StorageHelper
import com.obake.petcarepal.worker.NotificationScheduler

@Composable
fun Application(context: Context) {
    val database = ApplicationDatabase.getDatabase(context)
    val notificationScheduler = NotificationScheduler(context)
    val storageHelper = StorageHelper()
    val navController = rememberNavController()

    val overviewViewModel = OverviewViewModel(database.petDao(), navController)
    val activitiesViewModel = ActivitiesViewModel(database.activityDao(), notificationScheduler)
    val tipsViewModel = TipsViewModel(database.tipDao())
    val addPetViewModel = AddPetViewModel(database.petDao(), navController)

    PetCarePalTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                bottomBar = {
                    // TODO: Hide bottom bar on AddPetScreen
                    BottomAppBar {
                        Navigation(navController = navController)
                    }
                }
            ) { padding ->
                NavHost(navController = navController, startDestination = Screen.Home.name) {
                    composable(Screen.AddPet.name) {
                        AddPetScreen(addPetViewModel, storageHelper, modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Home.name) {
                        OverviewScreen(overviewViewModel, storageHelper, modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Activities.name) {
                        ActivitiesScreen(activitiesViewModel, modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Calendar.name) {
                        CalendarScreen(modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Tips.name) {
                        TipsScreen(tipsViewModel, modifier = Modifier.padding(padding))
                    }
                }
            }
        }
    }
}