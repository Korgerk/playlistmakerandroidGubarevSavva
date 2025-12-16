package com.example.playlist_maker_android_gubarevsavva.ui.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_gubarevsavva.data.di.Creator
import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.domain.repository.PlaylistsRepository
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TrackDetailsState(
    val track: Track,
    val playlists: List<Playlist> = emptyList()
)

class TrackDetailsViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository,
    initialTrack: Track
) : ViewModel() {

    private val _state = MutableStateFlow(TrackDetailsState(track = initialTrack))
    val state = _state.asStateFlow()

    init {
        observePlaylists()
        observeTrack(initialTrack)
    }

    private fun observePlaylists() {
        viewModelScope.launch {
            playlistsRepository.getAllPlaylists().collect { list ->
                _state.update { it.copy(playlists = list) }
            }
        }
    }

    private fun observeTrack(track: Track) {
        viewModelScope.launch {
            tracksRepository.getTrackByNameAndArtist(track).collect { found ->
                if (found != null) {
                    _state.update { it.copy(track = found) }
                }
            }
        }
    }

    fun toggleFavorite() {
        val current = _state.value.track
        val newFavorite = !current.favorite
        viewModelScope.launch {
            tracksRepository.updateTrackFavoriteStatus(current, newFavorite)
            _state.update { it.copy(track = current.copy(favorite = newFavorite)) }
        }
    }

    fun addToPlaylist(playlistId: Long) {
        val current = _state.value.track
        viewModelScope.launch {
            tracksRepository.insertTrackToPlaylist(current, playlistId)
            _state.update { it.copy(track = current.copy(playlistId = playlistId)) }
        }
    }

    companion object {
        fun getFactory(track: Track): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TrackDetailsViewModel(
                        playlistsRepository = Creator.getPlaylistsRepository(),
                        tracksRepository = Creator.getTracksRepository(),
                        initialTrack = track
                    ) as T
                }
            }
    }
}
