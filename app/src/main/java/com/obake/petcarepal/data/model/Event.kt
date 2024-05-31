package com.obake.petcarepal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
class Event(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    val id: Long,
    val name: String,
    val time: String,
    val date: String
)