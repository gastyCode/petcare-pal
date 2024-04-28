package com.obake.petcarepal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activity_id")
    val id: Long,
    val name: String,
    val time: String,
    val type: String,
    val icon: Int
)