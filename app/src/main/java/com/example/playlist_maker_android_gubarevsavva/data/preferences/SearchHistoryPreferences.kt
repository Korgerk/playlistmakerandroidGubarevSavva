package com.example.playlist_maker_android_gubarevsavva.data.preferences

import kotlinx.coroutines.flow.Flow

interface SearchHistoryPreferences {
    fun addEntry(word: String)
    fun observeEntries(): Flow<List<String>>
}
