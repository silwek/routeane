package com.silwek.routeane.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "routeane_prefs")

class AppPreferences(private val context: Context) {
    companion object {
        private val LAST_SELECTED_MODE_ID = intPreferencesKey("last_selected_mode_id")
    }

    fun getLastSelectedModeId(): Flow<Int?> {
        return context.dataStore.data
            .map { preferences ->
                preferences[LAST_SELECTED_MODE_ID]
            }
    }

    suspend fun setLastSelectedModeId(modeId: Int) {
        context.dataStore.edit { preferences ->
            preferences[LAST_SELECTED_MODE_ID] = modeId
        }
    }
}