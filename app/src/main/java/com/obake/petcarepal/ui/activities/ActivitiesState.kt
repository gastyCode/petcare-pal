package com.obake.petcarepal.ui.activities

import com.obake.petcarepal.data.model.Activity

data class ActivitiesState(
    val activities: List<Activity> = emptyList(),
    val openDialog: Boolean = false,
    val activityName: String = ""
)