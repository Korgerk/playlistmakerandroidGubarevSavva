package com.example.playlist_maker_android_gubarevsavva.data.storage

import com.example.playlist_maker_android_gubarevsavva.R
import com.example.playlist_maker_android_gubarevsavva.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackName = "Doomsday",
            artistName = "MF DOOM",
            trackTimeMillis = 258000,
            artworkUrl100 = null,
            collectionName = "Operation: Doomsday",
            releaseDate = "1999-04-20T00:00:00Z"
        ),
        TrackDto(
            trackName = "Accordion",
            artistName = "MF DOOM",
            trackTimeMillis = 205000,
            artworkUrl100 = null,
            collectionName = "Madvillainy",
            releaseDate = "2004-03-23T00:00:00Z"
        ),
        TrackDto(
            trackName = "All Caps",
            artistName = "MF DOOM",
            trackTimeMillis = 160000,
            artworkUrl100 = null,
            collectionName = "Madvillainy",
            releaseDate = "2004-03-23T00:00:00Z"
        ),
        TrackDto(
            trackName = "One Beer",
            artistName = "MF DOOM",
            trackTimeMillis = 212000,
            artworkUrl100 = null,
            collectionName = "MM..Food",
            releaseDate = "2004-11-16T00:00:00Z"
        ),
        TrackDto(
            trackName = "Fancy Clown",
            artistName = "MF DOOM",
            trackTimeMillis = 153000,
            artworkUrl100 = null,
            collectionName = "Madvillainy",
            releaseDate = "2004-03-23T00:00:00Z"
        ),
        TrackDto(
            trackName = "Beef Rapp",
            artistName = "MF DOOM",
            trackTimeMillis = 350000,
            artworkUrl100 = null,
            collectionName = "MM..Food",
            releaseDate = "2004-11-16T00:00:00Z"
        ),
        TrackDto(
            trackName = "Hoe Cakes",
            artistName = "MF DOOM",
            trackTimeMillis = 224000,
            artworkUrl100 = null,
            collectionName = "MM..Food",
            releaseDate = "2004-11-16T00:00:00Z"
        ),
        TrackDto(
            trackName = "Rapp Snitch Knishes",
            artistName = "MF DOOM",
            trackTimeMillis = 176000,
            artworkUrl100 = null,
            collectionName = "MM..Food",
            releaseDate = "2004-11-16T00:00:00Z"
        ),
        TrackDto(
            trackName = "Vomitspit",
            artistName = "MF DOOM",
            trackTimeMillis = 206000,
            artworkUrl100 = null,
            collectionName = "MM..Food",
            releaseDate = "2004-11-16T00:00:00Z"
        ),
        TrackDto(
            trackName = "Absolutely",
            artistName = "MF DOOM",
            trackTimeMillis = 168000,
            artworkUrl100 = null,
            collectionName = "Born Like This",
            releaseDate = "2009-03-24T00:00:00Z"
        ),

        TrackDto(
            trackName = "C.R.E.A.M.",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 252000,
            artworkUrl100 = null,
            collectionName = "Enter the Wu-Tang (36 Chambers)",
            releaseDate = "1993-11-09T00:00:00Z"
        ),
        TrackDto(
            trackName = "Protect Ya Neck",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 262000,
            artworkUrl100 = null,
            collectionName = "Enter the Wu-Tang (36 Chambers)",
            releaseDate = "1993-11-09T00:00:00Z"
        ),
        TrackDto(
            trackName = "Wu-Tang Clan Ain't Nuthing ta F' Wit",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 238000,
            artworkUrl100 = null,
            collectionName = "Enter the Wu-Tang (36 Chambers)",
            releaseDate = "1993-11-09T00:00:00Z"
        ),
        TrackDto(
            trackName = "Method Man",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 292000,
            artworkUrl100 = null,
            collectionName = "Enter the Wu-Tang (36 Chambers)",
            releaseDate = "1993-11-09T00:00:00Z"
        ),
        TrackDto(
            trackName = "Da Mystery of Chessboxin'",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 281000,
            artworkUrl100 = null,
            collectionName = "Enter the Wu-Tang (36 Chambers)",
            releaseDate = "1993-11-09T00:00:00Z"
        ),
        TrackDto(
            trackName = "Triumph",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 357000,
            artworkUrl100 = null,
            collectionName = "Wu-Tang Forever",
            releaseDate = "1997-06-03T00:00:00Z"
        ),
        TrackDto(
            trackName = "It's Yourz",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 247000,
            artworkUrl100 = null,
            collectionName = "Wu-Tang Forever",
            releaseDate = "1997-06-03T00:00:00Z"
        ),
        TrackDto(
            trackName = "Gravel Pit",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 242000,
            artworkUrl100 = null,
            collectionName = "The W",
            releaseDate = "2000-11-21T00:00:00Z"
        ),
        TrackDto(
            trackName = "Careful (Click, Click)",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 253000,
            artworkUrl100 = null,
            collectionName = "The W",
            releaseDate = "2000-11-21T00:00:00Z"
        ),
        TrackDto(
            trackName = "Back in the Game",
            artistName = "Wu-Tang Clan",
            trackTimeMillis = 287000,
            artworkUrl100 = null,
            collectionName = "Iron Flag",
            releaseDate = "2001-12-18T00:00:00Z"
        ),
        TrackDto(
            trackName = "1",
            artistName = "test",
            trackTimeMillis = 997000,
            artworkUrl100 = null,
            collectionName = "Test",
            releaseDate = "2025-12-16T00:00:00Z"
        )
    )

    fun search(request: String): List<TrackDto> {
        val query = request.lowercase()
        return listTracks.filter { track ->
            track.trackName.lowercase().contains(query) ||
                track.artistName.lowercase().contains(query)
        }.map { track ->
            track.copy(
                artworkUrl100 = resolveCover(track.collectionName)
            )
        }
    }

    private fun resolveCover(collectionName: String?): String? {
        collectionName ?: return null
        val pkg = "com.example.playlist_maker_android_gubarevsavva"
        return when {
            collectionName.contains("doomsday", true) -> "android.resource://$pkg/drawable/doomsday"
            collectionName.contains("madvillain", true) -> "android.resource://$pkg/drawable/madvillain"
            collectionName.contains("mm..food", true) -> "android.resource://$pkg/drawable/food"
            collectionName.contains("born like this", true) -> "android.resource://$pkg/drawable/born_like_this"
            collectionName.contains("36 chambers", true) || collectionName.contains("enter the wu", true) -> "android.resource://$pkg/drawable/enter"
            collectionName.contains("forever", true) -> "android.resource://$pkg/drawable/forever"
            collectionName.contains("the w", true) -> "android.resource://$pkg/drawable/the_w"
            collectionName.contains("iron flag", true) -> "android.resource://$pkg/drawable/iron_flag"
            else -> null
        }
    }
}

