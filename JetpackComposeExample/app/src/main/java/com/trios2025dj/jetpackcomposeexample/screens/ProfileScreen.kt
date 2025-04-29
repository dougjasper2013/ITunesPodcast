package com.trios2025dj.jetpackcomposeexample.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trios2025dj.jetpackcomposeexample.viewmodel.UserViewModel

@Composable
fun ProfileScreen(userViewModel: UserViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Enter your name", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") }
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") }
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            userViewModel.saveUser(firstName, lastName)
        }) {
            Text("Save")
        }
    }
}