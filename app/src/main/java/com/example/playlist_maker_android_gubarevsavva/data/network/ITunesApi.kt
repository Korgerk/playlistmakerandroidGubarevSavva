package com.example.playlist_maker_android_gubarevsavva.data.network

import com.example.playlist_maker_android_gubarevsavva.data.network.dto.ITunesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    @GET("search")
    fun search(
        @Query("term") term: String,
        @Query("media") media: String = "music",
        @Query("entity") entity: String = "song",
        @Query("limit") limit: Int = 25
    ): Call<ITunesSearchResponse>
}
