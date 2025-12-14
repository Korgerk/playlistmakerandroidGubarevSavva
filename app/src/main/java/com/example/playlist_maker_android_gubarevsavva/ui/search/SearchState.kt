package com.example.playlist_maker_android_gubarevsavva.ui.search

import com.example.playlist_maker_android_gubarevsavva.domain.model.Track

sealed class SearchState {
    data object Initial : SearchState()
    data object Searching : SearchState()
    data class Success(val tracks: List<Track>) : SearchState()
    data class Fail(val error: String) : SearchState()
}
