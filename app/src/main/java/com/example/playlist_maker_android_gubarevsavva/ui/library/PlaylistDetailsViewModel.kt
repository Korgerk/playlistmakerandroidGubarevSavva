package com.example.playlist_maker_android_gubarevsavva.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_gubarevsavva.data.di.Creator
import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist
import com.example.playlist_maker_android_gubarevsavva.domain.repository.PlaylistsRepository
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class PlaylistDetailsState(
    val playlist: Playlist? = null
)

class PlaylistDetailsViewModel(
    private val playlistId: Long,
    private val repository: PlaylistsRepository,
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PlaylistDetailsState())
    val state: StateFlow<PlaylistDetailsState> = _state
    private val _deleted = MutableStateFlow(false)
    val deleted: StateFlow<Boolean> = _deleted

    init {
        viewModelScope.launch {
            repository.getPlaylist(playlistId).collect { playlist ->
                _state.value = PlaylistDetailsState(playlist = playlist)
            }
        }
    }

    fun deletePlaylist() {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteTracksByPlaylistId(playlistId)
            repository.deletePlaylistById(playlistId)
            _deleted.value = true
        }
    }

    fun resetDeletionFlag() {
        _deleted.value = false
    }

    fun removeTrack(track: com.example.playlist_maker_android_gubarevsavva.domain.model.Track) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteTrackFromPlaylist(track)
        }
    }

    companion object {
        fun factory(playlistId: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistDetailsViewModel(
                        playlistId = playlistId,
                        repository = Creator.getPlaylistsRepository(),
                        tracksRepository = Creator.getTracksRepository()
                    ) as T
                }
            }
    }
}
