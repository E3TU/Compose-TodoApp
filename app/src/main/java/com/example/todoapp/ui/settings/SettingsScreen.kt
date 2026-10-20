package com.example.todoapp.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val materialYouEnabled by SettingsDataStore
        .materialYouEnabled(context)
        .collectAsStateWithLifecycle(initialValue = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text("Settings")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Material You color theme")
            Spacer(modifier = Modifier.width(16.dp))
            Switch(
                checked = materialYouEnabled,
                onCheckedChange = { enabled ->
                    scope.launch {
                        SettingsDataStore.setMaterialYouEnabled(context, enabled)
                    }
                }
            )
        }
    }
}
