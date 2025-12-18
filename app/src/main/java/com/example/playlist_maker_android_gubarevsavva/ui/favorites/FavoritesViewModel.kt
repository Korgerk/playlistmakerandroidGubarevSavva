package com.example.playlist_maker_android_gubarevsavva.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_gubarevsavva.data.di.Creator
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import com.example.playlist_maker_android_gubarevsavva.ui.model.UiTrack
import com.example.playlist_maker_android_gubarevsavva.ui.model.toDomain
import com.example.playlist_maker_android_gubarevsavva.ui.model.toUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _favorites =
        MutableStateFlow<ImmutableList<UiTrack>>(emptyList<UiTrack>().toImmutableList())
    val favorites: StateFlow<ImmutableList<UiTrack>> = _favorites

    init {
        viewModelScope.launch {
            tracksRepository.getFavoriteTracks().collect { list ->
                _favorites.value = list.map { it.toUi() }.toImmutableList()
            }
        }
    }

    fun removeFromFavorites(track: UiTrack) {
        viewModelScope.launch {
            tracksRepository.updateTrackFavoriteStatus(track.toDomain(), isFavorite = false)
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
