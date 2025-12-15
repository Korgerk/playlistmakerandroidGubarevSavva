package com.example.playlist_maker_android_gubarevsavva.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_gubarevsavva.data.di.Creator
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                val list = tracksRepository.searchTracks(expression = query)
                _searchScreenState.update { SearchState.Success(tracks = list) }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message.orEmpty()) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository()) as T
                }
            }
    }
}
