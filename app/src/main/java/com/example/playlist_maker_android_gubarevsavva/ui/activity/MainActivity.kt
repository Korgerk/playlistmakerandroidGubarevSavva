package com.example.playlist_maker_android_gubarevsavva.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_gubarevsavva.R
import com.example.playlist_maker_android_gubarevsavva.data.di.Creator
import com.example.playlist_maker_android_gubarevsavva.ui.navigation.PlaylistHost
import com.example.playlist_maker_android_gubarevsavva.ui.theme.PlaylistmakerandroidGubarevSavvaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PlaylistmakerandroidGubarevSavva)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Creator.init(applicationContext)
        setContent {
            var darkTheme by rememberSaveable { mutableStateOf(true) }
            PlaylistmakerandroidGubarevSavvaTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                PlaylistHost(
                    navController = navController,
                    darkTheme = darkTheme,
                    onThemeChange = { darkTheme = it }
                )
            }
        }
    }
}
