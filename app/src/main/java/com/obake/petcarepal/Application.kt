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
import com.obake.petcarepal.data.model.Pet
import com.obake.petcarepal.notification.AlarmScheduler
import com.obake.petcarepal.ui.activities.ActivitiesScreen
import com.obake.petcarepal.ui.activities.ActivitiesViewModel
import com.obake.petcarepal.ui.addpet.AddPetScreen
import com.obake.petcarepal.ui.addpet.AddPetViewModel
import com.obake.petcarepal.ui.events.EventsScreen
import com.obake.petcarepal.ui.components.Navigation
import com.obake.petcarepal.ui.events.EventsViewModel
import com.obake.petcarepal.ui.overview.OverviewScreen
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import com.obake.petcarepal.ui.tips.TipsScreen
import com.obake.petcarepal.ui.tips.TipsViewModel
import com.obake.petcarepal.util.StorageHelper

@Composable
fun Application(context: Context, pet: Pet?) {
    val database = ApplicationDatabase.getDatabase(context)
    val alarmScheduler = AlarmScheduler(context)
    val storageHelper = StorageHelper()
    val navController = rememberNavController()

    val activitiesViewModel = ActivitiesViewModel(database.activityDao(), alarmScheduler)
    val eventsViewModel = EventsViewModel(database.eventDao(), alarmScheduler)
    val addPetViewModel = AddPetViewModel(database.petDao(), navController)

    PetCarePalTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                bottomBar = {
                    if (pet != null) {
                        BottomAppBar {
                            Navigation(navController = navController)
                        }
                    }
                }
            ) { padding ->
                NavHost(navController = navController, startDestination = if (pet == null) Screen.AddPet.name else Screen.Home.name) {
                    composable(Screen.AddPet.name) {
                        AddPetScreen(addPetViewModel, storageHelper, modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Home.name) {
                        OverviewScreen(pet!!, storageHelper, modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Activities.name) {
                        ActivitiesScreen(activitiesViewModel, modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Events.name) {
                        EventsScreen(eventsViewModel, modifier = Modifier.padding(padding))
                    }
                    composable(Screen.Tips.name) {
                        val tipsViewModel = TipsViewModel(database.tipDao(), pet!!)
                        TipsScreen(tipsViewModel, modifier = Modifier.padding(padding))
                    }
                }
            }
        }
    }
}