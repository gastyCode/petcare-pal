package com.obake.petcarepal.ui.events

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obake.petcarepal.data.dao.EventDao
import com.obake.petcarepal.data.model.Event
import com.obake.petcarepal.notification.AlarmScheduler
import com.obake.petcarepal.util.DateHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class EventsViewModel(private val eventDao: EventDao, private val alarmScheduler: AlarmScheduler): ViewModel() {
    var state by mutableStateOf(EventsState())
        private set

    fun updateEvents(date: String) {
        viewModelScope.launch {
            state = state.copy(
                events = eventDao.getAllByDate(date).first().sortedBy { it.time }
            )
        }
    }

    fun handleEventAdd(name: String, timePickerState: TimePickerState, datePickerState: DatePickerState): Boolean {
        if (name.isBlank()) {
            return false
        }
        insert(name, timePickerState, datePickerState)
        return true
    }

    fun insert(name: String, timePickerState: TimePickerState, datePickerState: DatePickerState) {
        viewModelScope.launch {
            val time = DateHelper.dateStateToMillis(timePickerState, datePickerState)
            val timeString = DateHelper.timeStateToTime(timePickerState)
            val dateString = DateHelper.dateStateToDate(datePickerState)

            val event = Event(0, name, timeString, dateString)
            eventDao.insert(event)
            alarmScheduler.scheduleEvent(event.name, time, event.hashCode())
        }
    }

    fun delete(event: Event) {
        viewModelScope.launch {
            alarmScheduler.cancelEvent(event.hashCode())
            eventDao.delete(event)
        }
    }
}