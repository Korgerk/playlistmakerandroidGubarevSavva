package com.example.playlist_maker_android_gubarevsavva.ui.model

import androidx.compose.runtime.Immutable
import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist

@Immutable
data class PlaylistUi(
    val id: Long,
    val name: String,
    val description: String,
    val coverUri: String? = null,
    val tracks: List<UiTrack>
)

fun Playlist.toUi(): PlaylistUi =
    PlaylistUi(
        id = id,
        name = name,
        description = description,
        coverUri = coverUri,
        tracks = tracks.map { it.toUi() }
    )
