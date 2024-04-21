package com.obake.petcarepal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.obake.petcarepal.data.model.Tip
import kotlinx.coroutines.flow.Flow

@Dao
interface TipDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tip: Tip)
    @Update
    suspend fun update(tip: Tip)
    @Delete
    suspend fun delete(tip: Tip)
    @Query("SELECT * FROM tips WHERE tip_id = :id")
    fun get(id: Long): Flow<Tip>
    @Query("SELECT * FROM tips")
    fun getAll(): Flow<List<Tip>>
}