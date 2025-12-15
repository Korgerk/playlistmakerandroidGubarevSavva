package com.example.playlist_maker_android_gubarevsavva.data.storage

import com.example.playlist_maker_android_gubarevsavva.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackName = "گ'گ>گّگ?گٌگ?گ?‘?‘'گ?گَ 2000",
            artistName = "گ?‘?گ?گٌگü گ÷‘?گ?گ>‘?",
            trackTimeMillis = 158000
        ),
        TrackDto(
            trackName = "گ\"‘?‘?گُگُگّ گَ‘?گ?گ?گٌ",
            artistName = "گ?گٌگ?گ?",
            trackTimeMillis = 283000
        ),
        TrackDto(
            trackName = "گ?گç ‘?گ?گ?‘'‘?گٌ گ?گّگْگّگ?",
            artistName = "گ?‘?گٌ‘?",
            trackTimeMillis = 312000
        ),
        TrackDto(
            trackName = "گ-گ?گçگْگ?گّ گُگ? گٌگ?گçگ?گٌ گِگ?گ>گ?‘إگç",
            artistName = "گ?گٌگ?گ?",
            trackTimeMillis = 225000
        ),
        TrackDto(
            trackName = "گ>گ?گ?گ?گ?گ?",
            artistName = "گ?گَگ?گّ‘?گٌ‘?گ?",
            trackTimeMillis = 272000
        ),
        TrackDto(
            trackName = "گ?گّ گْگّ‘?گç",
            artistName = "گ?گ>‘?‘?گ?‘?",
            trackTimeMillis = 230000
        ),
        TrackDto(
            trackName = "گ?گç‘?گçگ?گçگ?",
            artistName = "گ?گٌگ?گ?",
            trackTimeMillis = 296000
        ),
        TrackDto(
            trackName = "گےگ?گْگ?گ?‘<گü ‘\"گ>گّگ?گٌگ?گ?گ?",
            artistName = "گِگُگ>گٌگ?",
            trackTimeMillis = 195000
        ),
        TrackDto(
            trackName = "گ÷گّگ?‘إگçگ?گّ‘'‘?",
            artistName = "گ?گçگ>‘?گ?گٌ‘إگّ",
            trackTimeMillis = 222000
        ),
        TrackDto(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 260000
        ),
        TrackDto(
            trackName = "گ‘'‘?گ?‘<گü گ+‘?گ?گç‘?",
            artistName = "گِگç‘?گçگ?گّ",
            trackTimeMillis = 241000
        ),
        TrackDto(
            trackName = "Doomsday",
            artistName = "MF DOOM",
            trackTimeMillis = 258000
        ),
        TrackDto(
            trackName = "Accordion",
            artistName = "MF DOOM",
            trackTimeMillis = 205000
        ),
        TrackDto(
            trackName = "Rapp Snitch Knishes",
            artistName = "MF DOOM",
            trackTimeMillis = 176000
        ),
        TrackDto(
            trackName = "All Caps",
            artistName = "MF DOOM",
            trackTimeMillis = 160000
        ),
        TrackDto(
            trackName = "Beef Rapp",
            artistName = "MF DOOM",
            trackTimeMillis = 350000
        ),
        TrackDto(
            trackName = "Hoe Cakes",
            artistName = "MF DOOM",
            trackTimeMillis = 224000
        ),
        TrackDto(
            trackName = "One Beer",
            artistName = "MF DOOM",
            trackTimeMillis = 212000
        ),
        TrackDto(
            trackName = "Vomitspit",
            artistName = "MF DOOM",
            trackTimeMillis = 206000
        ),
        TrackDto(
            trackName = "Fancy Clown",
            artistName = "MF DOOM",
            trackTimeMillis = 153000
        ),
        TrackDto(
            trackName = "Absolutely",
            artistName = "MF DOOM",
            trackTimeMillis = 168000
        )
    )

    fun search(request: String): List<TrackDto> {
        val query = request.lowercase()
        return listTracks.filter { track ->
            track.trackName.lowercase().contains(query) ||
                track.artistName.lowercase().contains(query)
        }
    }
}
