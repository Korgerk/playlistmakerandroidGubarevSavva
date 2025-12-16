package com.example.playlist_maker_android_gubarevsavva.data.db

import com.example.playlist_maker_android_gubarevsavva.data.db.entity.PlaylistEntity
import com.example.playlist_maker_android_gubarevsavva.data.db.entity.TrackEntity
import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track

fun PlaylistEntity.toDomain(tracks: List<Track>): Playlist =
    Playlist(
        id = id,
        name = name,
        description = description,
        coverUri = coverUri,
        tracks = tracks
    )

fun TrackEntity.toDomain(): Track =
    Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        album = album,
        year = year,
        artworkUrl = artworkUrl,
        playlistId = playlistId,
        favorite = favorite
    )

fun Track.toEntity(
    existingId: Long = 0,
    playlistIdOverride: Long? = playlistId,
    favoriteOverride: Boolean = favorite
): TrackEntity =
    TrackEntity(
        id = if (existingId != 0L) existingId else id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        album = album,
        year = year,
        artworkUrl = artworkUrl,
        playlistId = playlistIdOverride,
        favorite = favoriteOverride
    )
