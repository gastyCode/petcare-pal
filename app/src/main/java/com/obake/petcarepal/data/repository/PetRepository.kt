package com.obake.petcarepal.data.repository

import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.model.Pet

class PetRepository(private val petDao: PetDao) {
    suspend fun save(name: String, type: String, date: String, imageUrl: String) {
        val pet = Pet(0, name, type, date, imageUrl)
        petDao.insert(pet)
    }

    suspend fun update(pet: Pet) {
        petDao.update(pet)
    }

    suspend fun get(id: Long): Pet {
        return petDao.get(id)
    }

    suspend fun getAll(): List<Pet> {
        return petDao.getAll()
    }
}