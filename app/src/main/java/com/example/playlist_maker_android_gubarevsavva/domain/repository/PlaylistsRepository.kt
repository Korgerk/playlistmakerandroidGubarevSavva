package com.example.playlist_maker_android_gubarevsavva.domain.repository

import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getPlaylist(playlistId: Long): Flow<Playlist?>
    fun getAllPlaylists(): Flow<List<Playlist>>
    suspend fun addNewPlaylist(name: String, description: String, coverUri: String?)
    suspend fun deletePlaylistById(id: Long)
}
