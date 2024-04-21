package com.obake.petcarepal.ui.tips

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.obake.petcarepal.data.dao.TipDao
import com.obake.petcarepal.data.model.Tip
import kotlinx.coroutines.launch

class TipsViewModel(private val tipDao: TipDao): ViewModel() {
    var state by mutableStateOf(TipsState())
        private set

    init {
        viewModelScope.launch {
            tipDao.getAll().asLiveData().observeForever {
                state = state.copy(tips = it, currentTip = state.tips.firstOrNull())
            }
        }
    }

    fun nextTip() {
        val tips = state.tips
        val currentTip = state.currentTip
        if (tips.isNotEmpty()) {
            val index = if (currentTip == null) 0 else (tips.indexOf(currentTip) + 1) % tips.size
            state = state.copy(currentTip = tips[index])
        }
    }

    fun previousTip() {
        val tips = state.tips
        val currentTip = state.currentTip
        if (tips.isNotEmpty()) {
            val index = if (currentTip == null) 0 else (tips.indexOf(currentTip) - 1 + tips.size) % tips.size
            state = state.copy(currentTip = tips[index])
        }
    }

    fun delete(tip: Tip) {
        viewModelScope.launch {
            tipDao.delete(tip)
        }
    }

    fun insert(tip: Tip) {
        viewModelScope.launch {
            tipDao.insert(tip)
        }
    }

    fun update(tip: Tip) {
        viewModelScope.launch {
            tipDao.update(tip)
        }
    }
}