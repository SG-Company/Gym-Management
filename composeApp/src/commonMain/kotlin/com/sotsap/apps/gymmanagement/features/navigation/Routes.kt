package com.sotsap.apps.gymmanagement.features.navigation

/**
 * Defines the sealed interface for all possible navigation routes within the Gym Management application.
 *
 * This interface serves as a base for all specific route data classes.
 * Using a sealed interface ensures that all possible navigation destinations are known at compile time,
 * providing type safety and exhaustiveness when handling navigation events.
 */
sealed interface GymRoutes {

    /**
     * Represents the initial screen route, which is typically the first screen displayed to the user
     * when the application launches or when no other specific route is active.
     *
     * This route is used to navigate to the initial screen of the application.
     *
     * @property tag The unique identifier for the Initial route, defaults to "Initial".
     */
    data object Initial: Route {
        override val tag: String get() = "Initial"
    }

    /**
     * Represents the Login screen route.
     *
     * This route is used to navigate to the login screen of the application.
     *
     * @property tag The unique identifier for the Login route, defaults to "Login".
     */
    data object Login: Route {
        override val tag: String get() = "Login"
    }

}