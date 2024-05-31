package com.obake.petcarepal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.obake.petcarepal.data.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)
    @Update
    suspend fun update(event: Event)
    @Delete
    suspend fun delete(event: Event)
    @Query("SELECT * FROM events WHERE event_id = :id")
    fun get(id: Long): Flow<Event>
    @Query("SELECT * FROM events")
    fun getAll(): Flow<List<Event>>
    @Query("SELECT * FROM events WHERE date = :date")
    fun getAllByDate(date: String): Flow<List<Event>>
}