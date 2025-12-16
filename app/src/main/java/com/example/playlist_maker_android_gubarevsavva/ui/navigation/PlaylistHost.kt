package com.example.playlist_maker_android_gubarevsavva.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.ui.favorites.FavoritesScreen
import com.example.playlist_maker_android_gubarevsavva.ui.library.LibraryScreen
import com.example.playlist_maker_android_gubarevsavva.ui.library.PlaylistDetailsScreen
import com.example.playlist_maker_android_gubarevsavva.ui.library.PlaylistsViewModel
import com.example.playlist_maker_android_gubarevsavva.ui.main.MainScreen
import com.example.playlist_maker_android_gubarevsavva.ui.search.SearchScreen
import com.example.playlist_maker_android_gubarevsavva.ui.search.SearchViewModel
import com.example.playlist_maker_android_gubarevsavva.ui.track.TrackDetailsScreen
import com.example.playlist_maker_android_gubarevsavva.ui.library.NewPlaylistScreen
import com.example.playlist_maker_android_gubarevsavva.ui.settings.SettingsScreen

@Composable
fun PlaylistHost(
    navController: NavHostController,
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
        modifier = modifier
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSongsClick = { navController.navigate(Screen.SONGS.route) },
                onPlaylistsClick = { navController.navigate(Screen.PLAYLISTS.route) },
                onFavoritesClick = { navController.navigate(Screen.FAVORITES.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) }
            )
        }
        composable(Screen.SONGS.route) {
            val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = viewModel,
                onTrackClick = { track ->
                    val route = "${Screen.TRACK_DETAILS.route}/" +
                        "${Uri.encode(track.trackName)}/" +
                        "${Uri.encode(track.artistName)}/" +
                        "${Uri.encode(track.trackTime)}/" +
                        "${Uri.encode(track.album)}/" +
                        "${Uri.encode(track.year)}/" +
                        "${Uri.encode(track.artworkUrl.orEmpty())}"
                    navController.navigate(route)
                }
            )
        }
        composable(Screen.PLAYLISTS.route) {
            val viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.getFactory())
            LibraryScreen(
                onBackClick = { navController.popBackStack() },
                onAddNewPlaylist = { navController.navigate(Screen.NEW_PLAYLIST.route) },
                onPlaylistClick = { id -> navController.navigate("${Screen.PLAYLIST_DETAILS.route}/$id") },
                viewModel = viewModel
            )
        }
        composable(Screen.FAVORITES.route) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onTrackClick = { track ->
                    val route = "${Screen.TRACK_DETAILS.route}/" +
                        "${Uri.encode(track.trackName)}/" +
                        "${Uri.encode(track.artistName)}/" +
                        "${Uri.encode(track.trackTime)}/" +
                        "${Uri.encode(track.album)}/" +
                        "${Uri.encode(track.year)}/" +
                        "${Uri.encode(track.artworkUrl.orEmpty())}"
                    navController.navigate(route)
                }
            )
        }
        composable(Screen.NEW_PLAYLIST.route) {
            NewPlaylistScreen(onBackClick = { navController.popBackStack() })
        }
        composable(
            route = "${Screen.PLAYLIST_DETAILS.route}/{playlistId}",
            arguments = listOf(
                navArgument("playlistId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            PlaylistDetailsScreen(
                playlistId = playlistId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = "${Screen.TRACK_DETAILS.route}/{trackName}/{artistName}/{trackTime}/{album}/{year}/{artworkUrl}",
            arguments = listOf(
                navArgument("trackName") { type = NavType.StringType },
                navArgument("artistName") { type = NavType.StringType },
                navArgument("trackTime") { type = NavType.StringType },
                navArgument("album") { type = NavType.StringType },
                navArgument("year") { type = NavType.StringType },
                navArgument("artworkUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val trackName = backStackEntry.arguments?.getString("trackName").orEmpty()
            val artistName = backStackEntry.arguments?.getString("artistName").orEmpty()
            val trackTime = backStackEntry.arguments?.getString("trackTime").orEmpty()
            val album = backStackEntry.arguments?.getString("album").orEmpty()
            val year = backStackEntry.arguments?.getString("year").orEmpty()
            val artworkUrl = backStackEntry.arguments?.getString("artworkUrl").orEmpty().ifBlank { null }
            val track = Track(
                trackName = trackName,
                artistName = artistName,
                trackTime = trackTime,
                album = album,
                year = year,
                artworkUrl = artworkUrl
            )
            TrackDetailsScreen(
                track = track,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                darkTheme = darkTheme,
                onThemeToggle = { onThemeChange(!darkTheme) }
            )
        }
    }
}
