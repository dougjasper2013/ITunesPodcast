package com.trios2025dj.jetpackcomposeexample.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListScreen() {
    val items = List(20) { "Item #$it" }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { item ->
            ListItem(
                headlineContent = { Text(item) },
                supportingContent = { Text("This is a demo list item.") }
            )
            Divider()
        }
    }
}