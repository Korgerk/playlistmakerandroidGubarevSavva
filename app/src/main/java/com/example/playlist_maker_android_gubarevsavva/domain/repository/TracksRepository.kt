package com.example.playlist_maker_android_gubarevsavva.domain.repository

import com.example.playlist_maker_android_gubarevsavva.domain.model.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}
