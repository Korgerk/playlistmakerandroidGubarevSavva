package com.example.playlist_maker_android_gubarevsavva.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlist_maker_android_gubarevsavva.data.db.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: PlaylistEntity): Long

    @Query("SELECT * FROM playlists")
    fun getAll(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE id = :id LIMIT 1")
    fun getById(id: Long): Flow<PlaylistEntity?>

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deleteById(id: Long)
}
