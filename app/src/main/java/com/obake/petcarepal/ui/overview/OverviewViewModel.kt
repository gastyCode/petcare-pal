package com.obake.petcarepal.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.obake.petcarepal.data.Screen
import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.model.Pet
import kotlinx.coroutines.launch

class OverviewViewModel(private val petDao: PetDao, private val navController: NavController): ViewModel() {
    init {
        viewModelScope.launch {
            val petExists = petDao.count() > 0
            if (!petExists) {
                navController.popBackStack()
                navController.navigate(Screen.AddPet.name)
            }
        }
    }

    val pets: LiveData<List<Pet>> = petDao.getAll().asLiveData()

    fun delete(pet: Pet) {
        viewModelScope.launch {
            petDao.delete(pet)
        }
    }

    fun insert(pet: Pet) {
        viewModelScope.launch {
            petDao.insert(pet)
        }
    }

    fun update(pet: Pet) {
        viewModelScope.launch {
            petDao.update(pet)
        }
    }
}