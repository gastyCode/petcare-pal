package com.obake.petcarepal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.obake.petcarepal.data.daos.PetDao
import com.obake.petcarepal.data.models.Pet

@Database(entities = [Pet::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun petDao(): PetDao
}
