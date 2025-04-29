package com.trios2025dj.jetpackcomposeexample.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun SettingsScreen() {
    var text by remember { mutableStateOf("") }
    Column {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter name") }
        )
        if (text.isNotBlank()) {
            Text("Hello, $text!")
        }
    }
}