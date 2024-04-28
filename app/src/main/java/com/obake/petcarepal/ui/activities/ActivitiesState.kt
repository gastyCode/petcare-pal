package com.obake.petcarepal.ui.activities

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.obake.petcarepal.data.model.Activity

data class ActivitiesState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val activities: List<Activity> = emptyList(),
    val openDialog: Boolean = false,
    val openDropdown: Boolean = false,
    val activityName: String = "",
    val activityType: String = "",
    val activityIcon: Int = 0,
    val timePickerState: TimePickerState = TimePickerState(0, 0, true)
)