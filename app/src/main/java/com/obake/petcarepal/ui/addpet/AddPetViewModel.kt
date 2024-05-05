package com.obake.petcarepal.ui.addpet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.obake.petcarepal.data.Screen
import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.model.Pet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class AddPetViewModel(private val petDao: PetDao, private val navController: NavController): ViewModel() {
    var state by mutableStateOf(AddPetState())
        private set

    fun handleAddPet() {
        if (state.petName.isBlank() || state.petSpecie.isBlank() || state.petBirthdate.isBlank()) {
            state = state.copy(inputError = true)
            return
        }
        insert(state.petName, state.petSpecie, state.petBirthdate, state.petImage)
        navController.popBackStack()
        navController.navigate(Screen.Home.name)
    }

    fun toggleDialog() {
        state = state.copy(openDialog = !state.openDialog)
    }

    fun toggleDropdown() {
        state = state.copy(openDropdown = !state.openDropdown)
    }

    fun setPetName(petName: String) {
        state = state.copy(petName = petName, inputError = false)
    }

    fun setPetSpecie(petSpecie: String) {
        state = state.copy(petSpecie = petSpecie, inputError = false)
    }

    fun setPetBirthdate(petBirthdate: String) {
        state = state.copy(petBirthdate = petBirthdate, inputError = false)
    }

    fun insert(name: String, specie: String, birthdate: String, imageUrl: String) {
        viewModelScope.launch {
            petDao.insert(Pet(0, name, specie, birthdate, imageUrl))
        }
    }
}