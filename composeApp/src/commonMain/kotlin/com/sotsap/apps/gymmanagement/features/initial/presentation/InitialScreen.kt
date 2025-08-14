package com.sotsap.apps.gymmanagement.features.initial.presentation

import androidx.compose.runtime.Composable
import com.sotsap.apps.gymmanagement.core.compose.BaseScreen

typealias InitialS = InitialState
typealias InitialE = InitialEvent
typealias InitialV = InitialViewModel

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
) = BaseScreen<InitialS, InitialE, InitialV> { state, event, viewModel ->
    when (event) {
        is InitialEvent.NavigateToLogin -> onLogin()
        else -> {}
    }
}