package com.example.playlist_maker_android_gubarevsavva.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.playlist_maker_android_gubarevsavva.R
import com.example.playlist_maker_android_gubarevsavva.ui.model.UiTrack
import com.example.playlist_maker_android_gubarevsavva.ui.theme.PlaylistmakerandroidGubarevSavvaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    viewModel: SearchViewModel,
    onTrackClick: (UiTrack) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.searchScreenState.collectAsStateWithLifecycle()
    val history by viewModel.history.collectAsStateWithLifecycle(emptyList())
    var query by rememberSaveable { mutableStateOf("") }
    var isFieldFocused by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.search_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button_description)
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    if (it.isBlank()) {
                        viewModel.clearSearch()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFieldFocused = it.isFocused },
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus(force = true)
                        viewModel.search(query.trim())
                    }
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            focusManager.clearFocus(force = true)
                            viewModel.search(query.trim())
                        },
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (query.isNotEmpty()) {
                                query = ""
                                viewModel.clearSearch()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear_button_description),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(0.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface
                )
            )

            if (isFieldFocused && history.isNotEmpty() && query.isBlank()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(history) { item ->
                        Text(
                            text = item,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    query = item
                                    focusManager.clearFocus(force = true)
                                    viewModel.search(item)
                                }
                                .padding(vertical = 4.dp)
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                alpha = 0.3f
                            )
                        )
                    }
                }
                return@Column
            }

            when (val state = screenState) {
                is SearchState.Initial -> {
                    PlaceholderBox(
                        title = stringResource(id = R.string.search_placeholder_initial),
                        type = PlaceholderType.SEARCH
                    )
                }

                is SearchState.Searching -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                is SearchState.Success -> {
                    if (state.tracks.isEmpty()) {
                        PlaceholderBox(
                            title = stringResource(id = R.string.search_empty),
                            type = PlaceholderType.NOT_FOUND
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.tracks) { track ->
                                TrackListItem(
                                    track = track,
                                    onClick = { onTrackClick(track) }
                                )
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.5f
                                    )
                                )
                            }
                        }
                    }
                }

                is SearchState.Fail -> {
                    ErrorPlaceholder(
                        message = state.error.ifBlank { stringResource(id = R.string.search_error_generic) },
                        onRetry = {
                            focusManager.clearFocus(force = true)
                            viewModel.retryLast()
                        }
                    )
                }
            }
        }
    }
}

private enum class PlaceholderType { SEARCH, NOT_FOUND }

@Composable
private fun PlaceholderBox(title: String, type: PlaceholderType) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logoRes = when (type) {
            PlaceholderType.SEARCH -> (if (MaterialTheme.colorScheme.background.luminance() < 0.5f) R.drawable.icon_black_search else R.drawable.icon_white_search)
            PlaceholderType.NOT_FOUND -> (if (MaterialTheme.colorScheme.background.luminance() < 0.5f) R.drawable.icon_black_nf else R.drawable.icon_white_nf)
        }
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun ErrorPlaceholder(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logoRes =
            if (MaterialTheme.colorScheme.background.luminance() < 0.5f) R.drawable.icon_black_nf else R.drawable.icon_white_nf
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 12.dp)
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text(text = stringResource(id = R.string.search_retry))
        }
    }
}

@Composable
private fun TrackListItem(
    track: UiTrack,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
            contentDescription = "Обложка ${track.trackName}",
            modifier = Modifier.size(56.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                track.trackName,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(track.artistName, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(track.duration, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackRowPreview() {
    PlaylistmakerandroidGubarevSavvaTheme {
        TrackListItem(
            track = UiTrack(
                id = 0,
                trackName = "Doomsday",
                artistName = "MF DOOM",
                trackTimeMillis = 258000,
                duration = "04:18"
            )
        )
    }
}
