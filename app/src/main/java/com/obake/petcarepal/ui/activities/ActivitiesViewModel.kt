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
import com.obake.petcarepal.notification.AlarmScheduler
import com.obake.petcarepal.util.DateHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class ActivitiesViewModel(private val activityDao: ActivityDao, private val alarmScheduler: AlarmScheduler): ViewModel() {
    var state by mutableStateOf(ActivitiesState())
        private set

    init {
        viewModelScope.launch {
            activityDao.getAll().asLiveData().observeForever {
                state = state.copy(
                    activities = it.sortedBy { a -> a.time }
                )
            }
        }
    }

    fun delete(activity: Activity) {
        viewModelScope.launch {
            alarmScheduler.cancelActivity(activity.hashCode())
            activityDao.delete(activity)
        }
    }

    fun insert(name: String, type: String, icon: Int, timePickerState: TimePickerState) {
        viewModelScope.launch {
            val time = DateHelper.timeStateToMillis(timePickerState)
            val timeString = DateHelper.timeStateToTime(timePickerState)

            val activity = Activity(0, name, timeString, type, icon)
            activityDao.insert(activity)
            alarmScheduler.scheduleActivity(activity.name, time, activity.hashCode())
        }
    }
}