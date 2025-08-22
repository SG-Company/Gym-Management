package com.sotsap.apps.gymmanagement.features.home.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sotsap.apps.gymmanagement.core.models.Route
import kotlinx.serialization.Serializable

/**
 * Represents the profile scene.
 * This object is used for navigation and to identify the profile screen.
 */
@Serializable
object ProfileScene: Route()

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile screen")
    }
}