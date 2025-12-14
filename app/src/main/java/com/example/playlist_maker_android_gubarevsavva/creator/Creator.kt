package com.example.playlist_maker_android_gubarevsavva.creator

import com.example.playlist_maker_android_gubarevsavva.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_gubarevsavva.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}
