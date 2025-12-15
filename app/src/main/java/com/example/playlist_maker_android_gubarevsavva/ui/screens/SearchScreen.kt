package com.example.playlist_maker_android_gubarevsavva.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android_gubarevsavva.R
import com.example.playlist_maker_android_gubarevsavva.domain.model.Track
import com.example.playlist_maker_android_gubarevsavva.ui.search.SearchState
import com.example.playlist_maker_android_gubarevsavva.ui.search.SearchViewModel
import com.example.playlist_maker_android_gubarevsavva.ui.theme.PlaylistmakerandroidGubarevSavvaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }
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
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
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
                modifier = Modifier.fillMaxWidth(),
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
                        tint = Color.White
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
                            tint = Color.White
                        )
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    focusedTrailingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.White
                )
            )

            when (val state = screenState) {
                is SearchState.Initial -> {
                    BoxCenteredText(text = stringResource(id = R.string.search_placeholder))
                }

                is SearchState.Searching -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is SearchState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.tracks) { track ->
                            TrackListItem(track = track)
                            HorizontalDivider(color = Color.Gray.copy(alpha = 0.5f))
                        }
                    }
                }

                is SearchState.Fail -> {
                    BoxCenteredText(
                        text = state.error.ifBlank { stringResource(id = R.string.search_error_generic) },
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
private fun BoxCenteredText(text: String, color: Color = Color.Gray) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, color = color)
    }
}

@Composable
private fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "Трек ${track.trackName}"
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(track.trackName, fontWeight = FontWeight.Bold, color = Color.White)
            Text(track.artistName, color = Color.Gray)
        }
        Text(track.trackTime, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
private fun TrackRowPreview() {
    PlaylistmakerandroidGubarevSavvaTheme {
        TrackListItem(
            track = Track(
                trackName = "Doomsday",
                artistName = "MF DOOM",
                trackTime = "04:18"
            )
        )
    }
}
