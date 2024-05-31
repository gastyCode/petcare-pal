package com.obake.petcarepal.ui.events

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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class EventsViewModel(private val eventDao: EventDao, private val alarmScheduler: AlarmScheduler): ViewModel() {
    var state by mutableStateOf(EventsState())
        private set

    fun toggleDialog() {
        resetDialog()
        updateTimePickerState()
        state = state.copy(
            openDialog = !state.openDialog
        )
    }

    fun toggleCalendar() {
        state = state.copy(
            openCalendar = !state.openCalendar
        )
    }

    fun setEventName(eventName: String) {
        state = state.copy(
            eventName = eventName
        )
    }

    fun insert(name: String) {
        viewModelScope.launch {
            val time = DateHelper.dateStateToMillis(state.timePickerState, state.datePickerState)
            val timeString = DateHelper.timeStateToTime(state.timePickerState)
            val dateString = DateHelper.dateStateToDate(state.datePickerState)

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

    private fun updateTimePickerState() {
        val currentTime = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = currentTime
        state = state.copy(
            timePickerState = TimePickerState(calendar.get(java.util.Calendar.HOUR_OF_DAY), calendar.get(
                java.util.Calendar.MINUTE), true)
        )
    }

    private fun resetDialog() {
        state = state.copy(
            eventName = ""
        )
    }
}