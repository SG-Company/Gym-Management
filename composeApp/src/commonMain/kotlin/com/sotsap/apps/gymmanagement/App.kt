package com.sotsap.apps.gymmanagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sotsap.apps.gymmanagement.core.system.closeApp
import com.sotsap.apps.gymmanagement.features.login.presentation.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LoginScreen(
                insets = PaddingValues(),
                navController = rememberNavController(),
                onRegister = {},
                onLogin = {},
                onClose = {
                    closeApp()
                }
            )
        }
    }
}