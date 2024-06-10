package com.obake.petcarepal.ui.events

import android.app.AlarmManager
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import com.obake.petcarepal.R
import com.obake.petcarepal.data.model.Event
import com.obake.petcarepal.ui.components.AddItemButton
import com.obake.petcarepal.ui.components.AddItemDialog
import com.obake.petcarepal.ui.components.Background
import com.obake.petcarepal.ui.components.ItemCard
import com.obake.petcarepal.ui.theme.PetCarePalTheme
import com.obake.petcarepal.util.DateHelper
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(eventsViewModel: EventsViewModel, modifier: Modifier = Modifier) {
    val state = eventsViewModel.state
    val calendar = Calendar.getInstance()
    val calendarState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    val timePickerState = rememberTimePickerState(initialHour = calendar.get(Calendar.HOUR_OF_DAY), initialMinute = calendar.get(Calendar.MINUTE))

    var openDialog by rememberSaveable { mutableStateOf(false) }
    var inputError by rememberSaveable { mutableStateOf(false) }
    var calendarVisible by rememberSaveable { mutableStateOf(true) }
    var eventName by rememberSaveable { mutableStateOf("") }

    eventsViewModel.updateEvents(DateHelper.dateStateToDate(calendarState))

    val context = LocalContext.current
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService<AlarmManager>()!!
        when {
            !alarmManager.canScheduleExactAlarms() -> {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }

    AddItemDialog(
        openDialog = openDialog,
        inputError = inputError,
        nameValue = eventName,
        nameLabel = R.string.event_name,
        titleLabel = R.string.add_event,
        timePickerState = timePickerState,
        onAdd = {
            if (eventsViewModel.handleEventAdd(eventName, timePickerState, calendarState)) {
                inputError = false
                openDialog = false
            } else {
                inputError = true
            }
                },
        toggleDialog = { openDialog = !openDialog },
        onNameChange = { eventName = it }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Background(modifier = Modifier.matchParentSize())
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 8.dp, 0.dp)
                    .align(Alignment.CenterHorizontally),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                ToggleButton(
                    onIcon = Icons.Default.KeyboardArrowUp,
                    offIcon = Icons.Default.KeyboardArrowDown,
                    isOn = calendarVisible,
                    onSwitch = { calendarVisible = !calendarVisible },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp)
                )
                Calendar(
                    calendarVisible,
                    calendarState
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                EventList(
                    state.events,
                    eventsViewModel::delete,
                    {
                        openDialog = !openDialog
                    }
                )
            }
        }
    }
}

@Composable
fun EventList(list: List<Event>, deleteEvent: (Event) -> Unit, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val sortedList = list.sortedBy { it.time }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = stringResource(id = R.string.planned_events),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(sortedList) { event ->
                ItemCard(
                    name = event.name,
                    time = event.time,
                    onRemove = { deleteEvent(event) }
                )
            }
            item {
                AddItemButton(
                    onClick
                )
            }
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
fun ToggleButton(onIcon: ImageVector, offIcon: ImageVector, onSwitch: () -> Unit, modifier: Modifier = Modifier, isOn: Boolean = false) {
    Button(
        onClick = onSwitch,
        modifier = modifier
    ) {
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