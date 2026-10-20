package com.example.todoapp.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object SettingsDataStore {
    private val MATERIAL_YOU_ENABLED = booleanPreferencesKey("material_you_enabled")

    fun materialYouEnabled(context: Context): Flow<Boolean> =
        context.applicationContext.settingsDataStore.data.map { preferences ->
            preferences[MATERIAL_YOU_ENABLED] ?: false
        }

    suspend fun setMaterialYouEnabled(context: Context, enabled: Boolean) {
        context.applicationContext.settingsDataStore.edit { preferences ->
            preferences[MATERIAL_YOU_ENABLED] = enabled
        }
    }
}
