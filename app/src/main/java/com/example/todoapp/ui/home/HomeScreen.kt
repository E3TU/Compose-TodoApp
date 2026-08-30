package com.example.todoapp.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onSettingsClick: () -> Unit
) {
    Button(onClick = onSettingsClick,
        modifier = Modifier
            .padding(64.dp)) {
        Text("Settings")
    }
}
