package com.sotsap.apps.gymmanagement.core.models

import kotlinx.serialization.Serializable

/**
 * Represents a navigation route within the application.
 * This interface can be implemented by objects that define specific destinations
 * or screens that the user can navigate to.
 */
@Serializable
open class Route

/**
 * Represents a specific route within the "Home" section of the application.
 * This class likely serves as a base or a concrete implementation for navigation
 * destinations related to the home screen or its sub-sections.
 *
 * @property tag A string identifier for this specific home route. This can be used
 *               for various purposes like logging, tracking, or differentiating
 *               between different home-related screens.
 */
@Serializable
open class HomeRoute(val tag: String)