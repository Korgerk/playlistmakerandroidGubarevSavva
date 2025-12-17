package com.example.playlist_maker_android_gubarevsavva.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_gubarevsavva.data.di.Creator
import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.domain.repository.PlaylistsRepository
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

open class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists
    private var loadJob: Job? = null

    init {
        loadPlaylists()
    }

    fun createNewPlayList(namePlaylist: String, description: String, coverUri: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description, coverUri)
            loadPlaylists()
        }
    }

    fun loadPlaylists() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            playlistsRepository.getAllPlaylists().collect { playlist ->
                _playlists.value = playlist
            }
        }
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksRepository.insertTrackToPlaylist(track, playlistId)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksRepository.deleteTrackFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    fun deletePlaylist(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePlaylistById(id)
            loadPlaylists()
        }
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track = track).firstOrNull()
    }

    companion object {
        fun getFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistsViewModel(
                        playlistsRepository = Creator.getPlaylistsRepository(),
                        tracksRepository = Creator.getTracksRepository()
                    ) as T
                }
            }

        fun preview(): PlaylistsViewModel {
            return object : PlaylistsViewModel(
                playlistsRepository = Creator.getPlaylistsRepository(),
                tracksRepository = Creator.getTracksRepository()
            ) {}
        }
    }
}
