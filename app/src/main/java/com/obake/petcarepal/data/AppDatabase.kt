package com.obake.petcarepal.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.obake.petcarepal.R
import com.obake.petcarepal.data.dao.PetDao
import com.obake.petcarepal.data.dao.TipDao
import com.obake.petcarepal.data.model.Pet
import com.obake.petcarepal.data.model.Tip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

@Database(entities = [Pet::class, Tip::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun tipDao(): TipDao

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
                    .addCallback(PrepopulateCallback(context))
                    .build()
                    .also { INSTANCE = it}
            }
        }
    }
}

class PrepopulateCallback(private val context: Context): RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prepopulateDatabase(context)
        }
    }

    private suspend fun prepopulateDatabase(context: Context) {
        try {
            val tipDao = AppDatabase.getDatabase(context).tipDao()

            val tipList: JSONArray = context.resources.openRawResource(R.raw.tips).use {
                JSONArray(it.bufferedReader().use { it.readText() })
            }

            tipList.takeIf { it.length() > 0 }?.let {
                for (i in 0 until it.length()) {
                    val tip = it.getJSONObject(i)
                    tipDao.insert(
                        Tip(
                            id = tip.getLong("id"),
                            title = tip.getString("title"),
                            description = tip.getString("description")
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(
                "PrepopulateCallback",
                "Error prepopulating database: ${e.message}",
                e
            )
        }
    }
}
