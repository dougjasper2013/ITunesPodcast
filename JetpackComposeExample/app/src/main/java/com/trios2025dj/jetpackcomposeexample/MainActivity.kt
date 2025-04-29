package com.trios2025dj.jetpackcomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.trios2025dj.jetpackcomposeexample.screens.*
import com.trios2025dj.jetpackcomposeexample.ui.theme.JetpackComposeExampleTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeExampleTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Jetpack Compose Example") }) },
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("home") { HomeScreen() }
                        composable("list") { ListScreen() }
                        composable("settings") { SettingsScreen() }
                        composable("profile") { ProfileScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentRoute == "list",
            onClick = { navController.navigate("list") },
            label = { Text("List") },
            icon = { Icon(Icons.Default.List, contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentRoute == "settings",
            onClick = { navController.navigate("settings") },
            label = { Text("Settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) }
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") },
            label = { Text("Profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = null) }
        )

    }
}
