package com.obake.petcarepal.ui.events

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.obake.petcarepal.data.model.Event

data class EventsState(
    val events: List<Event> = emptyList(),
    val eventName: String = ""
)