package com.obake.petcarepal.ui.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.model.Pet

class SplashViewModel(private val petDao: PetDao): ViewModel() {
    val pets: LiveData<List<Pet>> = petDao.getAll().asLiveData()
}