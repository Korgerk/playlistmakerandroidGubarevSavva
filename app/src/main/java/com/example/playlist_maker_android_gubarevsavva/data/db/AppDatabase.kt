package com.example.playlist_maker_android_gubarevsavva.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlist_maker_android_gubarevsavva.data.db.dao.PlaylistDao
import com.example.playlist_maker_android_gubarevsavva.data.db.dao.TrackDao
import com.example.playlist_maker_android_gubarevsavva.data.db.entity.PlaylistEntity
import com.example.playlist_maker_android_gubarevsavva.data.db.entity.TrackEntity

@Database(
    entities = [PlaylistEntity::class, TrackEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun trackDao(): TrackDao
}
