package com.example.playlist_maker_android_gubarevsavva.data.network

import com.example.playlist_maker_android_gubarevsavva.data.dto.TrackDto
import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_gubarevsavva.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_gubarevsavva.data.network.dto.ITunesSearchResponse
import com.example.playlist_maker_android_gubarevsavva.domain.network.NetworkClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class RetrofitNetworkClient(
    private val client: OkHttpClient = defaultClient,
    private val api: ITunesApi = defaultApi(client)
) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val request = dto as TracksSearchRequest
        return try {
            val response = api.search(term = request.expression).execute()
            val body = response.body()
            val tracks = if (response.isSuccessful) mapToTracks(body) else emptyList()
            TracksSearchResponse(tracks).apply { resultCode = response.code() }
        } catch (e: Exception) {
            TracksSearchResponse(emptyList()).apply {
                resultCode = HttpURLConnection.HTTP_INTERNAL_ERROR
            }
        }
    }

    private fun mapToTracks(body: ITunesSearchResponse?): List<TrackDto> {
        return body?.results?.map { track ->
            TrackDto(
                trackName = track.trackName.orEmpty(),
                artistName = track.artistName.orEmpty(),
                trackTimeMillis = track.trackTimeMillis?.toInt() ?: 0,
                artworkUrl100 = track.artworkUrl100,
                collectionName = track.collectionName.orEmpty(),
                releaseDate = track.releaseDate
            )
        }.orEmpty()
    }

    companion object {
        private val defaultClient: OkHttpClient by lazy {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        private fun defaultApi(client: OkHttpClient): ITunesApi =
            Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ITunesApi::class.java)
    }
}
