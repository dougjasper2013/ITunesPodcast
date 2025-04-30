package com.trios2025dj.jetpackcomposeexample.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.trios2025dj.jetpackcomposeexample.viewmodel.UserViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(userViewModel: UserViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Enter your name", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            singleLine = true,
            modifier = Modifier.focusRequester(focusRequester)
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            userViewModel.saveUser(firstName, lastName)
            firstName = ""
            lastName = ""
            Toast.makeText(context, "User saved successfully!", Toast.LENGTH_SHORT).show()

            coroutineScope.launch {
                awaitFrame() // Wait for recomposition
                focusRequester.requestFocus()
            }
        }) {
            Text("Save")
        }
    }
}