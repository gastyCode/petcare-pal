package com.obake.petcarepal.ui.events

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.obake.petcarepal.data.model.Event

@OptIn(ExperimentalMaterial3Api::class)
data class EventsState(
    val events: List<Event> = emptyList(),
    val eventName: String = "",
    val timePickerState: TimePickerState = TimePickerState(0, 0, true)
)