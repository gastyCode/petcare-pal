package com.obake.petcarepal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.obake.petcarepal.data.model.Activity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: Activity)
    @Update
    suspend fun update(activity: Activity)
    @Delete
    suspend fun delete(activity: Activity)
    @Query("SELECT * FROM activities WHERE activity_id = :id")
    fun get(id: Long): Flow<Activity>

    @Query("SELECT * FROM activities")
    fun getAll(): Flow<List<Activity>>
}