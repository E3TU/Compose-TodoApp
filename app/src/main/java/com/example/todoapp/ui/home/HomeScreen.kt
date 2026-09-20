package com.example.todoapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.ui.todo.AddTodoBottomSheet
import com.example.todoapp.ui.todo.Todo
import com.example.todoapp.ui.todo.TodoViewModel
import java.nio.file.WatchEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSettingsClick: () -> Unit, viewModel: TodoViewModel = viewModel()) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                title = {
                    Text("Todos")
                },
                actions = {
                    IconButton(
                        onClick = onSettingsClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )

        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Todo"
                    )
                },
                text = {
                    Text("Add Todo")
                }
            )
        },

    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(todos, key = { it.id }) { todo ->
                TodoRow(
                    todo = todo,
                    onToggle = { viewModel.toggle(todo) },
                    onDelete = { viewModel.delete(todo) }
                )
            }
        }
    }
    if (showBottomSheet) {
        AddTodoBottomSheet(
            onDismiss = {
                showBottomSheet = false
            },
            onConfirm = { title, description ->
                viewModel.addTodo(title, description)
                showBottomSheet = false

            }
        )
    }
}
@Composable
fun TodoRow(
    todo: Todo,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge
            )
            if (todo.description.isNotBlank()) {
                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Checkbox(checked = todo.completed, onCheckedChange = { onToggle() })
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
