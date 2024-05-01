package com.obake.petcarepal.ui.addpet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obake.petcarepal.data.Screen
import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.model.Pet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class AddPetViewModel(private val petDao: PetDao, val navigateToNext: (String) -> Unit): ViewModel() {
    var state by mutableStateOf(AddPetState())
        private set

    init {
        viewModelScope.launch {
            if (petDao.count() > 0) {
                navigateToNext(Screen.Home.name)
            }
        }
    }

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

    fun insert(name: String, specie: String, birthdate: String, imageUrl: String) {
        viewModelScope.launch {
            petDao.insert(Pet(0, name, specie, birthdate, imageUrl))
        }
    }
}