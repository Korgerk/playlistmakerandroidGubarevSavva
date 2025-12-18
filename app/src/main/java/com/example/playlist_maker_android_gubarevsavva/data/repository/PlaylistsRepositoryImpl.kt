package com.example.playlist_maker_android_gubarevsavva.data.repository

import com.example.playlist_maker_android_gubarevsavva.data.db.dao.PlaylistDao
import com.example.playlist_maker_android_gubarevsavva.data.db.dao.TrackDao
import com.example.playlist_maker_android_gubarevsavva.data.db.entity.PlaylistEntity
import com.example.playlist_maker_android_gubarevsavva.data.db.toDomain
import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist
import com.example.playlist_maker_android_gubarevsavva.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PlaylistsRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val trackDao: TrackDao
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return combine(
            playlistDao.getById(playlistId),
            trackDao.getAll()
        ) { playlist, tracks ->
            playlist?.toDomain(tracks.filter { it.playlistId == playlistId }.map { it.toDomain() })
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return combine(
            playlistDao.getAll(),
            trackDao.getAll()
        ) { playlists, tracks ->
            playlists.map { entity ->
                val playlistTracks =
                    tracks.filter { it.playlistId == entity.id }.map { it.toDomain() }
                entity.toDomain(playlistTracks)
            }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverUri: String?) {
        playlistDao.insert(
            PlaylistEntity(
                name = name,
                description = description,
                coverUri = coverUri
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistDao.deleteById(id)
        trackDao.clearPlaylistId(id)
    }
}
