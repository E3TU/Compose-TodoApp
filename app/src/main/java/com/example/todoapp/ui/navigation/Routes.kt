package com.example.todoapp.ui.navigation
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Settings : Screen("settings")
}
