package com.trios2025dj.jetpackcomposeexample.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    private val FIRST_NAME = stringPreferencesKey("first_name")
    private val LAST_NAME = stringPreferencesKey("last_name")

    val firstName: Flow<String> = context.dataStore.data.map { it[FIRST_NAME] ?: "" }
    val lastName: Flow<String> = context.dataStore.data.map { it[LAST_NAME] ?: "" }

    suspend fun saveFirstName(name: String) {
        context.dataStore.edit { it[FIRST_NAME] = name }
    }

    suspend fun saveLastName(name: String) {
        context.dataStore.edit { it[LAST_NAME] = name }
    }
}