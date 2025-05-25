package com.example.employeetaskreg.data.repsitory

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("jwt_token")
    private val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")
    private val APP_THEME_KEY = booleanPreferencesKey("app_theme")


    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "employee_task_reg")

    val tokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }

    val searchHistoryFlow: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            val historyString = preferences[SEARCH_HISTORY_KEY] ?: ""
            if (historyString.isNotEmpty()) {
                historyString.split(",").toList()
            } else {
                emptyList()
            }
        }

    suspend fun storeToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }


    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun storeSearchHistory(searchHistory: List<String>){
        context.dataStore.edit {preferences ->
            preferences[SEARCH_HISTORY_KEY] = searchHistory.joinToString(",")
        }
    }

    suspend fun clearSearchHistory(){
        context.dataStore.edit {preferences ->
            preferences.remove(SEARCH_HISTORY_KEY)
        }
    }

    val isDarkMode: Flow<Boolean>  = context.dataStore.data.map { preferences ->
            preferences[APP_THEME_KEY] ?: false
        }

    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[APP_THEME_KEY] = isDarkMode
        }
    }
}