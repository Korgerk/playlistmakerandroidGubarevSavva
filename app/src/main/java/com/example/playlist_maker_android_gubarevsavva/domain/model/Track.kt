package com.example.playlist_maker_android_gubarevsavva.domain.model

data class Track(
    val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val album: String = "",
    val year: String = "",
    val artworkUrl: String? = null,
    val playlistId: Long? = null,
    val favorite: Boolean = false
)
