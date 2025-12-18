package com.example.playlist_maker_android_gubarevsavva.domain.repository

import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    fun getTrackByNameAndArtist(track: Track): Flow<Track?>
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)
    suspend fun deleteTrackFromPlaylist(track: Track)
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    suspend fun deleteTracksByPlaylistId(playlistId: Long)
    fun observeHistory(): Flow<List<String>>
    suspend fun addToHistory(query: String)
}
