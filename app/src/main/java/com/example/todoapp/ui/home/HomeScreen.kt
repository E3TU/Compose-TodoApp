package com.example.todoapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.ui.todo.TodoBottomSheet
import com.example.todoapp.ui.todo.Todo
import com.example.todoapp.ui.todo.TodoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSettingsClick: () -> Unit, viewModel: TodoViewModel = viewModel()) {
    val todos by viewModel.todos.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    var editingTodo by remember { mutableStateOf<Todo?>(null) }
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
                    editingTodo = null
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(todos, key = { it.id }) { todo ->
                TodoRow(
                    todo = todo,
                    onClick = {
                        editingTodo = todo
                        showBottomSheet = true
                    },
                    onToggle = { viewModel.toggle(todo) },
                    onDelete = { viewModel.delete(todo) }
                )
            }
        }
    }
    if (showBottomSheet) {
        TodoBottomSheet(
            todo = editingTodo,
            onDismiss = {
                showBottomSheet = false
            },
            onConfirm = { id, title, description ->
                if (id == null) {
                    viewModel.addTodo(title, description)
                } else {
                    viewModel.updateTodo(id, title, description)
                }
                showBottomSheet = false
            }
        )
    }
}

@Composable
fun TodoRow(
    todo: Todo,
    onClick: () -> Unit,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(vertical = 24.dp, horizontal = 12.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = todo.completed, onCheckedChange = { onToggle() })
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 19.sp,
                textDecoration = if (todo.completed) TextDecoration.LineThrough else null,
                color = if (todo.completed) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.onSurface
            )
            if (todo.description.isNotBlank()) {
                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = if (todo.completed) TextDecoration.LineThrough else null,
                    color = if (todo.completed) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }
        IconButton(onClick = onClick) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
