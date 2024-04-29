package com.obake.petcarepal.data

import com.obake.petcarepal.R

enum class Screen {
    Home,
    Activities,
    Calendar,
    Tips,
    AddPet
}

data class NavigationScreen(val screen: Screen, val icon: Int) {
    companion object {
        val screens = listOf(
            NavigationScreen(Screen.Home, R.drawable.baseline_pets_24),
            NavigationScreen(Screen.Activities, R.drawable.baseline_list_24),
            NavigationScreen(Screen.Calendar, R.drawable.baseline_event_24),
            NavigationScreen(Screen.Tips, R.drawable.baseline_tips_and_updates_24)
        )
    }
}