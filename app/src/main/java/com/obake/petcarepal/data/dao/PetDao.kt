package com.obake.petcarepal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.obake.petcarepal.data.model.Pet

@Dao
interface PetDao {
    @Insert
    suspend fun insert(pet: Pet)
    @Update
    suspend fun update(pet: Pet)
    @Query("SELECT * FROM pets WHERE id = :id")
    suspend fun get(id: Long): Pet
    @Query("SELECT * FROM pets")
    suspend fun getAll(): List<Pet>
}