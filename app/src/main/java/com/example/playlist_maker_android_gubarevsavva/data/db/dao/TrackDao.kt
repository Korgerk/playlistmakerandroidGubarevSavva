package com.example.playlist_maker_android_gubarevsavva.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.playlist_maker_android_gubarevsavva.data.db.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Upsert
    suspend fun upsert(track: TrackEntity): Long

    @Query("SELECT * FROM tracks")
    fun getAll(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE playlistId = :playlistId")
    fun getByPlaylist(playlistId: Long): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE favorite = 1")
    fun getFavorites(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist LIMIT 1")
    fun findByNameAndArtistFlow(name: String, artist: String): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist LIMIT 1")
    suspend fun findByNameAndArtist(name: String, artist: String): TrackEntity?

    @Query("UPDATE tracks SET playlistId = NULL WHERE playlistId = :playlistId")
    suspend fun clearPlaylistId(playlistId: Long)
}
