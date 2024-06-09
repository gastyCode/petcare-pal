package com.obake.petcarepal.ui.events

import com.obake.petcarepal.data.model.Event

data class EventsState(
    val events: List<Event> = emptyList()
)