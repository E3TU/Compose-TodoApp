package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalContext
import com.example.todoapp.ui.navigation.AppNavigation
import com.example.todoapp.ui.settings.SettingsDataStore
import com.example.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppThemeWrapper {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppThemeWrapper(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val materialYouEnabled by SettingsDataStore
        .materialYouEnabled(context)
        .collectAsStateWithLifecycle(initialValue = false)

    TodoAppTheme(dynamicColor = materialYouEnabled) {
        content()
    }
}