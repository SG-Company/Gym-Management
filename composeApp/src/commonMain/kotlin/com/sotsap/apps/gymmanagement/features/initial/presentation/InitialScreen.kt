package com.sotsap.apps.gymmanagement.features.initial.presentation

import androidx.compose.runtime.Composable
import com.sotsap.apps.gymmanagement.core.compose.BaseScreen
import com.sotsap.apps.gymmanagement.core.models.Route
import kotlinx.serialization.Serializable

typealias InitialS = InitialState
typealias InitialE = InitialEvent
typealias InitialV = InitialViewModel

/**
 * Represents the initial scene of the application.
 * This object is used to define the starting point of the application's navigation graph.
 * It is typically used in conjunction with a navigation library, such as Jetpack Navigation,
 * to specify the initial destination of the app.
 */
@Serializable
object InitialScene: Route()

/**
 * Composable function that represents the initial screen of the application.
 *
 * This screen is the first screen that the user sees when they open the app.
 * It is responsible for handling the initial setup and navigation of the app.
 *
 * @param onLogin A callback function that is invoked when the user logs in.
 */
@Composable
fun InitialScreen(
    onLogin: () -> Unit = {},
    onHome: () -> Unit = {}
) = BaseScreen<InitialS, InitialE, InitialV> { state, event, viewModel ->
    when (event) {
        is InitialEvent.NavigateToLogin -> onLogin()
        is InitialEvent.NavigateToHome -> onHome()
        else -> {}
    }
}