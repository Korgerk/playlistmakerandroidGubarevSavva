package com.example.playlist_maker_android_gubarevsavva.data.network

import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_gubarevsavva.data.storage.Storage
import com.example.playlist_maker_android_gubarevsavva.domain.network.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {
    override fun doRequest(dto: Any): TracksSearchResponse {
        val searchList = storage.search((dto as TracksSearchRequest).expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}
