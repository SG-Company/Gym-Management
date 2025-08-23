package com.sotsap.apps.gymmanagement.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sotsap.apps.gymmanagement.features.initial.presentation.InitialScene
import com.sotsap.apps.gymmanagement.features.initial.presentation.InitialScreen
import com.sotsap.apps.gymmanagement.features.login.presentation.LoginScene
import com.sotsap.apps.gymmanagement.features.login.presentation.LoginScreen
import com.sotsap.apps.gymmanagement.modes.admin.home.features.home.host.presentation.AdminHomeHostScreen
import com.sotsap.apps.gymmanagement.modes.admin.home.features.home.host.presentation.AdminHomeScene

/**
 * Composable function that defines the navigation graph for the Gym Management application.
 *
 * This function sets up the `NavHost` which manages the navigation between different screens
 * (composable) in the app. It defines the start destination and the composable functions
 * associated with each navigation route.
 *
 * @param controller The [NavHostController] used to navigate between destinations.
 * @param innerPaddingValues [PaddingValues] to be applied to screens that require it,
 * typically for handling system UI insets like status bars or navigation bars.
 */
@Composable
fun GymNavigationHost(
    controller: NavHostController,
    innerPaddingValues: PaddingValues
) {
    NavHost(
        navController = controller,
        startDestination = InitialScene
    ) {
        composable<InitialScene> {
            InitialScreen(
                onLogin = {
                    controller.navigate(LoginScene)
                },
                onHome = {
                    controller.popBackStack()
                    // TODO: Navigate to home screen (admin or client)
                    controller.navigate(AdminHomeScene)
                }
            )
        }
        composable<LoginScene> {
            LoginScreen(
                insets = innerPaddingValues,
                onSuccessLogin = {
                    controller.popBackStack()
                    controller.navigate(AdminHomeScene)
                }
            )
        }
        composable<AdminHomeScene> { AdminHomeHostScreen() }
    }
}
