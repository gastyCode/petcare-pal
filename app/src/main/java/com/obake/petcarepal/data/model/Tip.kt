package com.obake.petcarepal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tips")
data class Tip(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tip_id")
    val id: Long = 0,
    val title: String,
    val description: String,
    val specie: String
)