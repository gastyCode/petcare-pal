package com.obake.petcarepal.ui.activities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.obake.petcarepal.data.dao.ActivityDao
import com.obake.petcarepal.data.model.Activity
import kotlinx.coroutines.launch

class ActivitiesViewModel(private val activityDao: ActivityDao): ViewModel() {
    var state by mutableStateOf(ActivitiesState())
        private set

    init {
        viewModelScope.launch {
            activityDao.getAll().asLiveData().observeForever {
                state = state.copy(activities = it, openDialog = false)
            }
        }
    }

    fun toggleDialog() {
        state = state.copy(openDialog = !state.openDialog)
    }

    fun delete(activity: Activity) {
        viewModelScope.launch {
            activityDao.delete(activity)
        }
    }

    fun insert(activity: Activity) {
        viewModelScope.launch {
            activityDao.insert(activity)
        }
    }

    fun update(activity: Activity) {
        viewModelScope.launch {
            activityDao.update(activity)
        }
    }
}