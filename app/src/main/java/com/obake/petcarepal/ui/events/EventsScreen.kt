package com.obake.petcarepal.ui.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.obake.petcarepal.R
import com.obake.petcarepal.data.model.Event
import com.obake.petcarepal.ui.components.AddItemButton
import com.obake.petcarepal.ui.components.AddItemDialog
import com.obake.petcarepal.ui.components.ItemCard
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(eventsViewModel: EventsViewModel, modifier: Modifier = Modifier) {
    val state = eventsViewModel.state

    AddItemDialog(
        openDialog = state.openDialog,
        nameValue = state.eventName,
        label = R.string.add_event,
        timePickerState = state.timePickerState,
        onAdd = { eventsViewModel.insert(state.eventName) },
        toggleDialog = eventsViewModel::toggleDialog,
        onNameChange = eventsViewModel::setEventName
    )

    Box(modifier = Modifier.then(modifier)) {
        Column {
            ToggleButton(
                onIcon = Icons.Default.KeyboardArrowDown,
                offIcon = Icons.Default.KeyboardArrowUp,
                isOn = state.openCalendar,
                onSwitch = eventsViewModel::toggleCalendar
            )
            Calendar(
                state.openDialog,
                state.datePickerState
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AddItemButton(
                    onClick = eventsViewModel::toggleDialog,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.End)
                )
                EventList(state.events, eventsViewModel::delete)
            }
        }
    }
}

@Composable
fun EventList(list: List<Event>, deleteEvent: (Event) -> Unit, modifier: Modifier = Modifier) {
    val sortedList = list.sortedBy { it.time }

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(sortedList) { event ->
            ItemCard(
                name = event.name,
                time = event.time,
                onRemove = { deleteEvent(event) },
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(isVisible: Boolean, datePickerState: DatePickerState, modifier: Modifier = Modifier) {
    if (isVisible) {
        DatePicker(
            state = datePickerState,
            modifier = modifier
        )
    }
}

@Composable
fun ToggleButton(onIcon: ImageVector, offIcon: ImageVector, isOn: Boolean = false, onSwitch: () -> Unit) {
    Button(onClick = onSwitch) {
        if (isOn) {
            Icon(imageVector = onIcon, contentDescription = "On")
        } else {
            Icon(imageVector = offIcon, contentDescription = "Off")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarScreenPreview() {
    PetCarePalTheme {
    }
}