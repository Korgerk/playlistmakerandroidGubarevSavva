package com.example.playlist_maker_android_gubarevsavva.ui.common

import java.util.concurrent.TimeUnit

fun formatDuration(millis: Long): String {
    if (millis <= 0) return "00:00"
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
    return "%02d:%02d".format(minutes, seconds)
}
