package com.obake.petcarepal.data

import com.obake.petcarepal.R

enum class Screen {
    Home,
    Activities,
    Calendar,
    Tips,
    AddPet
}

data class NavigationScreen(val screen: Screen, val icon: Int, val resName: Int) {
    companion object {
        val screens = listOf(
            NavigationScreen(Screen.Home, R.drawable.baseline_pets_24, R.string.home),
            NavigationScreen(Screen.Activities, R.drawable.baseline_list_24, R.string.activities),
            NavigationScreen(Screen.Calendar, R.drawable.baseline_event_24, R.string.calendar),
            NavigationScreen(Screen.Tips, R.drawable.baseline_tips_and_updates_24, R.string.tips)
        )
    }
}