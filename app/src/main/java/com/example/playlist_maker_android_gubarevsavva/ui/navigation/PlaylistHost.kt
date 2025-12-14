package com.example.playlist_maker_android_gubarevsavva.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlist_maker_android_gubarevsavva.ui.library.LibraryScreen
import com.example.playlist_maker_android_gubarevsavva.ui.main.MainScreen
import com.example.playlist_maker_android_gubarevsavva.ui.search.SearchScreen
import com.example.playlist_maker_android_gubarevsavva.ui.search.SearchViewModel
import com.example.playlist_maker_android_gubarevsavva.ui.settings.SettingsScreen

@Composable
fun PlaylistHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
        modifier = modifier
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onLibraryClick = { navController.navigate(Screen.LIBRARY.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) }
            )
        }
        composable(Screen.SEARCH.route) {
            val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable(Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Screen.LIBRARY.route) {
            LibraryScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
