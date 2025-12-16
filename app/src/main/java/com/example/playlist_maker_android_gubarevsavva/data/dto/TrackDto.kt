package com.example.playlist_maker_android_gubarevsavva.data.dto

data class TrackDto(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String? = null,
    val collectionName: String? = null,
    val releaseDate: String? = null
)
