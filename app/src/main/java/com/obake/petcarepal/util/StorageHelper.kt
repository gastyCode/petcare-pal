package com.obake.petcarepal.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StorageHelper {
    private val Context.storeData: DataStore<Preferences> by preferencesDataStore(name = "data")
    suspend fun saveImage(context: Context, name: String, value: String) {
        context.storeData.edit { preferences ->
            preferences[stringPreferencesKey(name)] = value
        }
    }
    fun loadImage(context: Context, name: String): Flow<String?> {
        return context.storeData.data.map { preferences ->
            preferences[stringPreferencesKey(name)]
        }
    }
}