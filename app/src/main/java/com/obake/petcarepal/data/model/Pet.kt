package com.obake.petcarepal.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pet_id")
    val id: Long = 0,
    val name: String,
    val type: String,
    val date: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)