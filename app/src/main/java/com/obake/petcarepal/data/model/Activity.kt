package com.obake.petcarepal.data.model

import androidx.room.Entity

@Entity(tableName = "activities")
data class Activity (
    val name: String,
    val type: String,
    val date: String,
    val imageUrl: String
)