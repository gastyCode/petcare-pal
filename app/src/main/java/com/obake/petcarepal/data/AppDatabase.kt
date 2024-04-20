package com.obake.petcarepal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.model.Pet

@Database(entities = [Pet::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun petDao(): PetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "petcarepal_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it}
            }
        }
    }
}
