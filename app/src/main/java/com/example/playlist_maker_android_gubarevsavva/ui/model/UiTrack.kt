package com.example.playlist_maker_android_gubarevsavva.ui.model

import androidx.compose.runtime.Immutable
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.ui.common.formatDuration

@Immutable
data class UiTrack(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val duration: String,
    val album: String = "",
    val year: String = "",
    val artworkUrl: String? = null,
    val playlistId: Long? = null,
    val favorite: Boolean = false
)

fun Track.toUi(): UiTrack =
    UiTrack(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTimeMillis = trackTimeMillis,
        duration = formatDuration(trackTimeMillis),
        album = album,
        year = year,
        artworkUrl = artworkUrl,
        playlistId = playlistId,
        favorite = favorite
    )

fun UiTrack.toDomain(): Track =
    Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTimeMillis = trackTimeMillis,
        album = album,
        year = year,
        artworkUrl = artworkUrl,
        playlistId = playlistId,
        favorite = favorite
    )
