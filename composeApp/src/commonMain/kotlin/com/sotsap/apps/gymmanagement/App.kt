package com.sotsap.apps.gymmanagement

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sotsap.apps.gymmanagement.core.framework.theme.GymTheme
import com.sotsap.apps.gymmanagement.features.navigation.GymNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    GymTheme {

        val navController = rememberNavController()

        Scaffold { innerPadding ->
            GymNavigation(
                navController = navController,
                insets = innerPadding
            )
        }
    }
}