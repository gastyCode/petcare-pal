package com.obake.petcarepal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val type: String,
    val date: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)