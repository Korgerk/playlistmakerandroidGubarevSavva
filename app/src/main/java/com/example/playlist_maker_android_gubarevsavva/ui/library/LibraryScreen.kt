package com.example.playlist_maker_android_gubarevsavva.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.playlist_maker_android_gubarevsavva.ui.theme.PlaylistmakerandroidGubarevSavvaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onBackClick: () -> Unit,
    onAddNewPlaylist: () -> Unit,
    onPlaylistClick: (Long) -> Unit,
    viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.getFactory()),
    modifier: Modifier = Modifier
) {
    val playlists by viewModel.playlists.collectAsState(emptyList())
    var deleteTargetId by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Плейлисты") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewPlaylist,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(0.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (playlists.isEmpty()) {
                    Text(text = "Плейлисты", color = MaterialTheme.colorScheme.onSurface)
                    Text(
                        text = "Создайте свой первый плейлист",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(playlists, key = { it.id }) { playlist ->
                            PlaylistRow(
                                playlist = playlist,
                                onClick = { onPlaylistClick(playlist.id) },
                                onLongClick = { deleteTargetId = playlist.id }
                            )
                            HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                        }
                    }
                }
            }
        }

        val targetId = deleteTargetId
        if (targetId != null) {
            AlertDialog(
                onDismissRequest = { deleteTargetId = null },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deletePlaylist(targetId)
                        deleteTargetId = null
                    }) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { deleteTargetId = null }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                },
                title = { Text(text = stringResource(id = R.string.delete_playlist_title)) },
                text = { Text(text = stringResource(id = R.string.delete_playlist_message)) }
            )
        }
    }
}

@Composable
private fun PlaylistRow(
    playlist: Playlist,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (playlist.coverUri != null) {
            AsyncImage(
                model = playlist.coverUri,
                contentDescription = playlist.name,
                modifier = Modifier.size(56.dp)
            )
        } else {
            Image(
                painter = painterResource(id = if (MaterialTheme.colorScheme.background.luminance() < 0.5f) R.drawable.ic_music_white else R.drawable.ic_music_black),
                contentDescription = playlist.name,
                modifier = Modifier.size(56.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
            Text(text = playlist.name, color = MaterialTheme.colorScheme.onSurface)
            if (playlist.description.isNotBlank()) {
                Text(text = playlist.description, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            val trackCount = playlist.tracks.size
            Text(text = "$trackCount треков", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    PlaylistmakerandroidGubarevSavvaTheme {
        LibraryScreen(
            onBackClick = {},
            onAddNewPlaylist = {},
            onPlaylistClick = {},
            viewModel = PlaylistsViewModel.preview()
        )
    }
}
