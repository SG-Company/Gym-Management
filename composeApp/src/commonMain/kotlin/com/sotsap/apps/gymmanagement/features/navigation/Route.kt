package com.sotsap.apps.gymmanagement.features.navigation

/**
 * Represents a navigation route within the application.
 *
 * Each route is identified by a unique [tag] string.
 * This class is open to allow for extension and creation of specific route types.
 */
interface Route{

    /**
     * The unique identifier for this navigation route.
     * This is used to navigate to this destination and to identify it in the backstack.
     */
    val tag: String

    /**
     * Returns the unique identifier for this navigation route.
     *
     * @return The [tag] string associated with this route.
     */
    fun get() = tag

}