package com.sotsap.apps.gymmanagement.features.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sotsap.apps.gymmanagement.features.initial.presentation.InitialScreen
import com.sotsap.apps.gymmanagement.features.login.presentation.LoginScreen

/**
 * Composable function that defines the navigation graph for the Gym Management application.
 *
 * It uses a [NavHost] to define the different destinations and their corresponding composable
 * functions. The [navController] is used to navigate between these destinations.
 *
 * @param navController The [NavController] used to manage navigation within the app.
 * @param insets The padding values representing the insets of the screen.
 */
@Composable
fun GymNavigation(navController: NavHostController, insets: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = GymRoutes.Initial.tag
    ) {
        initialComposable(controller = navController, insets = insets)
    }
}

/**
 * Defines the composable for the initial screen of the application.
 *
 * This extension function on [NavGraphBuilder] sets up the composable
 * that will be displayed when the route matching [GymRoutes.Initial.tag]
 * is navigated to.
 * @param controller The [NavController] used to navigate within the app.
 * @param insets The padding values representing the insets of the screen.
 */
private fun NavGraphBuilder.initialComposable(controller: NavHostController, insets: PaddingValues) {
    composable(route = GymRoutes.Initial.tag) {
        InitialScreen(
            onLogin = {
                controller.popBackStack()
                controller.navigate(GymRoutes.Login.tag)
            }
        )
    }
    composable(route = GymRoutes.Login.tag) {
        LoginScreen(
            insets = insets
        )
    }
}