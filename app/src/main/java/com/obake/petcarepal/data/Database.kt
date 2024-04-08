package com.obake.petcarepal.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.model.Pet

@Database(entities = [Pet::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun petDao(): PetDao
}
