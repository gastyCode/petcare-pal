package com.obake.petcarepal.ui.addpet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.obake.petcarepal.data.dao.PetDao
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
class AddPetViewModel(private val petDao: PetDao): ViewModel() {
    var state by mutableStateOf(AddPetState())
        private set

    fun toggleDialog() {
        state = state.copy(openDialog = !state.openDialog)
    }

    fun setPetName(petName: String) {
        state = state.copy(petName = petName)
    }

    fun setPetSpecie(petSpecie: String) {
        state = state.copy(petSpecie = petSpecie)
    }

    fun setPetBirthdate(petBirthdate: String) {
        state = state.copy(petBirthdate = petBirthdate)
    }

    fun millisToDate(milliseconds: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        val format = SimpleDateFormat("DD.MM.YYYY")
        return format.format(calendar.time)
    }
}