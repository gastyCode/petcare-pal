package com.obake.petcarepal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.obake.petcarepal.data.model.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pet: Pet)
    @Update
    suspend fun update(pet: Pet)
    @Delete
    suspend fun delete(pet: Pet)
    @Query("SELECT * FROM pets WHERE pet_id = :id")
    fun get(id: Long): Flow<Pet>
    @Query("SELECT * FROM pets")
    fun getAll(): Flow<List<Pet>>
    @Query("SELECT COUNT(*) FROM pets")
    suspend fun count(): Int
}