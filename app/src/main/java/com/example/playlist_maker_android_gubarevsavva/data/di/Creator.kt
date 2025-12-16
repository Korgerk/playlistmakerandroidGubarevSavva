package com.example.playlist_maker_android_gubarevsavva.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.playlist_maker_android_gubarevsavva.data.db.AppDatabase
import com.example.playlist_maker_android_gubarevsavva.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_gubarevsavva.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android_gubarevsavva.data.repository.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_gubarevsavva.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_gubarevsavva.data.storage.Storage
import com.example.playlist_maker_android_gubarevsavva.domain.repository.PlaylistsRepository
import com.example.playlist_maker_android_gubarevsavva.domain.repository.TracksRepository

object Creator {
    private lateinit var appContext: Context

    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("search_history.preferences_pb") }
        )
    }

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "playlist_maker_db"
        ).build()
    }

    private val networkClient by lazy { RetrofitNetworkClient(Storage()) }
    private val searchHistoryPreferences by lazy { SearchHistoryPreferences(dataStore) }
    private val tracksRepository by lazy {
        TracksRepositoryImpl(
            networkClient = networkClient,
            trackDao = database.trackDao(),
            historyPreferences = searchHistoryPreferences
        )
    }
    private val playlistsRepository by lazy {
        PlaylistsRepositoryImpl(
            playlistDao = database.playlistDao(),
            trackDao = database.trackDao()
        )
    }

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun getTracksRepository(): TracksRepository = tracksRepository
    fun getPlaylistsRepository(): PlaylistsRepository = playlistsRepository
}
