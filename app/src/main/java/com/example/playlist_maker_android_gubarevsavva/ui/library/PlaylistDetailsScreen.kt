package com.example.playlist_maker_android_gubarevsavva.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playlist_maker_android_gubarevsavva.R
import com.example.playlist_maker_android_gubarevsavva.domain.model.Playlist
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.ui.theme.PlaylistmakerandroidGubarevSavvaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailsScreen(
    playlistId: Long,
    onBackClick: () -> Unit,
    viewModel: PlaylistDetailsViewModel = viewModel(factory = PlaylistDetailsViewModel.factory(playlistId))
) {
    val state by viewModel.state.collectAsState()
    val playlist = state.playlist
    val deleted by viewModel.deleted.collectAsState()

    LaunchedEffect(deleted) {
        if (deleted) {
            onBackClick()
            viewModel.resetDeletionFlag()
        }
    }

    PlaylistDetailsContent(
        playlist = playlist,
        onBackClick = onBackClick,
        onDelete = { viewModel.deletePlaylist() },
        onRemoveTrack = { viewModel.removeTrack(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaylistDetailsContent(
    playlist: Playlist?,
    onBackClick: () -> Unit,
    onDelete: () -> Unit,
    onRemoveTrack: (Track) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = playlist?.name ?: stringResource(id = R.string.library_button)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button_description)
                        )
                    }
                },
                actions = {
                    if (playlist != null) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.delete))
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        if (playlist == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.search_empty), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (playlist.coverUri != null) {
                    AsyncImage(
                        model = playlist.coverUri,
                        contentDescription = playlist.name,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(180.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(
                            id = if (MaterialTheme.colorScheme.background.luminance() < 0.5f) R.drawable.ic_music_white else R.drawable.ic_music_black
                        ),
                        contentDescription = playlist.name,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(180.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
                Text(text = playlist.description.ifBlank { stringResource(id = R.string.favorites_empty) }, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "Треков: ${playlist.tracks.size}", color = MaterialTheme.colorScheme.onSurface)

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(playlist.tracks, key = { it.id }) { track ->
                        TrackRow(
                            track = track,
                            onLongClick = { trackToDelete = track }
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog && playlist != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDelete()
                }) {
                    Text(text = stringResource(id = R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            title = { Text(text = stringResource(id = R.string.delete_playlist_title)) },
            text = { Text(text = stringResource(id = R.string.delete_playlist_message)) }
        )
    }

    val pendingTrack = trackToDelete
    if (pendingTrack != null) {
        AlertDialog(
            onDismissRequest = { trackToDelete = null },
            confirmButton = {
                TextButton(onClick = {
                    trackToDelete = null
                    onRemoveTrack(pendingTrack)
                }) {
                    Text(text = stringResource(id = R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { trackToDelete = null }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            title = { Text(text = stringResource(id = R.string.delete)) },
            text = { Text(text = pendingTrack.trackName) }
        )
    }
}

@Composable
private fun TrackRow(track: Track, onLongClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val placeholderRes = if (MaterialTheme.colorScheme.background.luminance() < 0.5f) {
            R.drawable.ic_music_white
        } else {
            R.drawable.ic_music_black
        }
        AsyncImage(
            model = track.artworkUrl,
            placeholder = painterResource(id = placeholderRes),
            error = painterResource(id = placeholderRes),
            contentDescription = track.trackName,
            modifier = Modifier.size(56.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = track.trackName, color = MaterialTheme.colorScheme.onSurface)
            Text(text = track.artistName, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = track.trackTime, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview
@Composable
private fun PlaylistDetailsScreenPreview() {
    PlaylistmakerandroidGubarevSavvaTheme {
        PlaylistDetailsContent(
            playlist = Playlist(
                id = 1,
                name = "Плейлист",
                description = "Описание",
                tracks = listOf(
                    Track(trackName = "Трек 1", artistName = "Исполнитель", trackTime = "03:15"),
                    Track(trackName = "Трек 2", artistName = "Исполнитель", trackTime = "04:20")
                )
            ),
            onBackClick = {},
            onDelete = {},
            onRemoveTrack = {}
        )
    }
}
