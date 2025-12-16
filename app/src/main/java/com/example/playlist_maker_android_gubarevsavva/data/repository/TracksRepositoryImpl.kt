package com.example.playlist_maker_android_gubarevsavva.data.repository

import com.example.playlist_maker_android_gubarevsavva.data.db.dao.TrackDao
import com.example.playlist_maker_android_gubarevsavva.data.db.toDomain
import com.example.playlist_maker_android_gubarevsavva.data.db.toEntity
import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_gubarevsavva.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.domain.network.NetworkClient
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import java.io.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackDao: TrackDao,
    private val historyPreferences: SearchHistoryPreferences
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000) // imitate network delay
        if (response.resultCode != 200) {
            throw IOException("Ошибка сервера")
        }
        return (response as TracksSearchResponse).results.map { dto ->
            val seconds = dto.trackTimeMillis / 1000
            val minutes = seconds / 60
            val trackTime = "%02d:%02d".format(minutes, seconds - minutes * 60)
            val year = dto.releaseDate?.toString()?.take(4).orEmpty()
            val artwork = dto.artworkUrl100?.toString()
            Track(
                trackName = dto.trackName,
                artistName = dto.artistName,
                trackTime = trackTime,
                album = dto.collectionName.orEmpty(),
                year = year,
                artworkUrl = artwork
            )
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return trackDao.findByNameAndArtistFlow(track.trackName, track.artistName).map { it?.toDomain() }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavorites().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        val existing = trackDao.findByNameAndArtist(track.trackName, track.artistName)
        if (existing?.playlistId == playlistId) return
        val entity = track.toEntity(
            existingId = existing?.id ?: 0,
            playlistIdOverride = playlistId,
            favoriteOverride = existing?.favorite ?: track.favorite
        )
        trackDao.upsert(entity)
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        val existing = trackDao.findByNameAndArtist(track.trackName, track.artistName) ?: return
        trackDao.upsert(existing.copy(playlistId = null))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val existing = trackDao.findByNameAndArtist(track.trackName, track.artistName)
        val entity = track.toEntity(
            existingId = existing?.id ?: 0,
            playlistIdOverride = existing?.playlistId,
            favoriteOverride = isFavorite
        )
        trackDao.upsert(entity)
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        trackDao.clearPlaylistId(playlistId)
    }

    override fun observeHistory(): Flow<List<String>> = historyPreferences.observeEntries()

    override suspend fun addToHistory(query: String) {
        historyPreferences.addEntry(query)
    }
}
