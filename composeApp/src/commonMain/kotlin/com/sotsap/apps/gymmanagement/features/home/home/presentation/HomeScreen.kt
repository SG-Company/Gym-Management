package com.sotsap.apps.gymmanagement.features.home.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sotsap.apps.gymmanagement.core.models.HomeRoute
import com.sotsap.apps.gymmanagement.core.models.Route
import kotlinx.serialization.Serializable

/**
 * Represents the home screen of the application.
 * This is the main landing screen after a user successfully logs in.
 * It typically displays an overview or quick access to various features.
 */
@Serializable
object HomeScene: Route()

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home screen")
    }
}