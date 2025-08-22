package com.sotsap.apps.gymmanagement

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sotsap.apps.gymmanagement.core.framework.theme.GymTheme
import com.sotsap.apps.gymmanagement.navigation.GymNavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * The main entry point for the Gym Management application.
 *
 * This composable function sets up the overall structure of the app, including:
 * - Applying the [GymTheme].
 * - Initializing the [rememberNavController] for navigation.
 * - Using a [Scaffold] to provide a basic Material Design layout structure.
 * - Hosting the [GymNavigationHost] composable, which defines the app's navigation graph.
 */
@Composable
@Preview
fun App() {
    GymTheme {

        val navController = rememberNavController()

        Scaffold { innerPadding ->
            GymNavigationHost(
                controller = navController,
                innerPaddingValues = innerPadding
            )
        }
    }
}