package com.obake.petcarepal.data.model

import com.obake.petcarepal.R

data class NavigationItem(val title: String, val route: String, val icon: Int) {
    companion object {
        val items = listOf(
            NavigationItem("Pet", "Home", R.drawable.baseline_pets_24),
            NavigationItem("Activities", "Activities", R.drawable.baseline_list_24),
            NavigationItem("Calendar", "Calendar", R.drawable.baseline_event_24),
            NavigationItem("Tips", "Tips", R.drawable.baseline_tips_and_updates_24),
        )
    }
}