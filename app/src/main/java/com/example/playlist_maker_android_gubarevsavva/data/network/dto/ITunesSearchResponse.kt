package com.example.playlist_maker_android_gubarevsavva.data.network.dto

data class ITunesSearchResponse(
    val resultCount: Int? = null,
    val results: List<ITunesTrack> = emptyList()
)

data class ITunesTrack(
    val trackName: String? = null,
    val artistName: String? = null,
    val trackTimeMillis: Long? = null,
    val collectionName: String? = null,
    val releaseDate: String? = null,
    val artworkUrl100: String? = null
)
