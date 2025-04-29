package com.trios2025dj.jetpackcomposeexample.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trios2025dj.jetpackcomposeexample.viewmodel.UserViewModel

@Composable
fun HomeScreen(userViewModel: UserViewModel = viewModel()) {
    val user by userViewModel.user.collectAsState()

    var count by remember { mutableStateOf(0) }
    val displayName = if (user.first.isNotEmpty()) "${user.first} ${user.last}" else "Guest"

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Welcome, $displayName!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { count++ }) {
            Text("Click count: $count")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}