package com.example.playlist_maker_android_gubarevsavva.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val album: String,
    val year: String,
    val artworkUrl: String?,
    val playlistId: Long?,
    val favorite: Boolean
)
