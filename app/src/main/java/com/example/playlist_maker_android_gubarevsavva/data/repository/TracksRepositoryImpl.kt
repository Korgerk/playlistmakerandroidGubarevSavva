package com.example.playlist_maker_android_gubarevsavva.data.repository

import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.domain.network.NetworkClient
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository
import kotlinx.coroutines.delay

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000) // эмуляция сетевой задержки
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { dto ->
                val seconds = dto.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(minutes, seconds - minutes * 60)
                Track(dto.trackName, dto.artistName, trackTime)
            }
        } else {
            emptyList()
        }
    }
}
