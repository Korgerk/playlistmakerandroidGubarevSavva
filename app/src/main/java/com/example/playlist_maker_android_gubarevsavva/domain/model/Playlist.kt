package com.example.playlist_maker_android_gubarevsavva.domain.model

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverUri: String? = null,
    val tracks: List<Track>
)
