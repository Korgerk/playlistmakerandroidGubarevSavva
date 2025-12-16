package com.example.playlist_maker_android_gubarevsavva.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_gubarevsavva.data.di.Creator
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Track>>(emptyList())
    val favorites: StateFlow<List<Track>> = _favorites

    init {
        viewModelScope.launch {
            tracksRepository.getFavoriteTracks().collect { list ->
                _favorites.value = list
            }
        }
    }

    fun removeFromFavorites(track: Track) {
        viewModelScope.launch {
            tracksRepository.updateTrackFavoriteStatus(track, isFavorite = false)
        }
    }

    companion object {
        fun factory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoritesViewModel(
                        tracksRepository = Creator.getTracksRepository()
                    ) as T
                }
            }
    }
}
