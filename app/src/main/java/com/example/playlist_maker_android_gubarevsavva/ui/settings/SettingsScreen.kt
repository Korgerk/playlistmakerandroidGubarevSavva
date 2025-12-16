package com.example.playlist_maker_android_gubarevsavva.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android_gubarevsavva.R
import com.example.playlist_maker_android_gubarevsavva.ui.theme.PlaylistmakerandroidGubarevSavvaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    darkTheme: Boolean,
    onThemeToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val shareMessage = stringResource(id = R.string.share_message)
    val developerEmail = stringResource(id = R.string.developer_email)
    val emailSubject = stringResource(id = R.string.email_subject)
    val emailBody = stringResource(id = R.string.email_body)
    val agreementUrl = stringResource(id = R.string.user_agreement_url)

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button_description)
                        )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareMessage)
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(id = R.string.share_button))
                }
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(developerEmail))
                            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                            putExtra(Intent.EXTRA_TEXT, emailBody)
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(id = R.string.support_button))
                }
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(agreementUrl))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(id = R.string.user_agreement_button))
                }
            }

            OutlinedButton(
                onClick = { onThemeToggle() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Смена темы",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Switch(
                        checked = darkTheme,
                        onCheckedChange = { onThemeToggle() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    PlaylistmakerandroidGubarevSavvaTheme {
        SettingsScreen(onBackClick = {}, darkTheme = true, onThemeToggle = {})
    }
}
