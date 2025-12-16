package com.example.playlist_maker_android_gubarevsavva.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob())
) {

    fun addEntry(word: String) {
        if (word.isEmpty()) return
        coroutineScope.launch {
            dataStore.edit { preferences ->
                val historyString = preferences[PREFERENCES_KEY].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }
                history.remove(word)
                history.add(0, word)
                val limited = mutableListOf<String>().apply {
                    for (item in history) {
                        if (size == MAX_ENTRIES) break
                        add(item)
                    }
                }
                preferences[PREFERENCES_KEY] = limited.joinToString(SEPARATOR)
            }
        }
    }

    suspend fun getEntries(): List<String> {
        val preferences = dataStore.data.map { it[PREFERENCES_KEY].orEmpty() }.first()
        if (preferences.isBlank()) return emptyList()
        return preferences.split(SEPARATOR)
    }

    fun observeEntries(): Flow<List<String>> =
        dataStore.data.map { prefs ->
            prefs[PREFERENCES_KEY].orEmpty()
                .takeIf { it.isNotBlank() }
                ?.split(SEPARATOR)
                .orEmpty()
        }

    companion object {
        private val PREFERENCES_KEY = stringPreferencesKey("search_history")
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
    }
}
