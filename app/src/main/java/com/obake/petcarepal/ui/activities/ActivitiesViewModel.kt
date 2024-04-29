package com.obake.petcarepal.ui.activities

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.obake.petcarepal.data.dao.ActivityDao
import com.obake.petcarepal.data.model.Activity
import com.obake.petcarepal.util.DateHelper
import com.obake.petcarepal.worker.NotificationScheduler
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
class ActivitiesViewModel(private val activityDao: ActivityDao, private val notificationScheduler: NotificationScheduler): ViewModel() {
    var state by mutableStateOf(ActivitiesState())
        private set

    init {
        viewModelScope.launch {
            activityDao.getAll().asLiveData().observeForever {
                state = state.copy(
                    activities = it,
                    openDialog = false,
                    openDropdown = false,
                    timePickerState = state.timePickerState
                )
            }
        }
    }

    fun toggleDialog() {
        resetDialog()
        updateTimePickerState()
        state = state.copy(openDialog = !state.openDialog)
    }

    fun toggleDropdown() {
        state = state.copy(openDropdown = !state.openDropdown)
    }

    fun setActivityName(name: String) {
        state = state.copy(
            activities = state.activities,
            openDialog = state.openDialog,
            activityName = name
        )
    }

    fun setActivityIcon(type: String, icon: Int) {
        state = state.copy(
            activities = state.activities,
            openDialog = state.openDialog,
            activityType = type,
            activityIcon = icon
        )
    }

    fun delete(activity: Activity) {
        viewModelScope.launch {
            notificationScheduler.cancel(activity.id)
            activityDao.delete(activity)
        }
    }

    fun insert(name: String, type: String, icon: Int) {
        viewModelScope.launch {
            val time = DateHelper.timeStateToMillis(state.timePickerState)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time

            // TODO 1: Change the time format to locale specific
            val format = SimpleDateFormat("HH:mm")
            val timeString = format.format(calendar.time)

            notificationScheduler.schedule(time, name, time)
            activityDao.insert(Activity(0, name, timeString, type, icon))

            resetDialog()
        }
    }

    fun update(activity: Activity) {
        viewModelScope.launch {
            activityDao.update(activity)
        }
    }

    private fun updateTimePickerState() {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTime
        state = state.copy(
            activities = state.activities,
            openDialog = state.openDialog,
            activityName = state.activityName,
            timePickerState = TimePickerState(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        )
    }

    private fun resetDialog() {
        state = state.copy(
            activities = state.activities,
            openDialog = state.openDialog,
            openDropdown = state.openDropdown,
            activityName = "",
            activityType = "",
            activityIcon = 0,
            timePickerState = state.timePickerState
        )
    }
}