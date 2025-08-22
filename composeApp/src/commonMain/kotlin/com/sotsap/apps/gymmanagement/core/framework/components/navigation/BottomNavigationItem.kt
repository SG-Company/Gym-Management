package com.sotsap.apps.gymmanagement.core.framework.components.navigation

import com.sotsap.apps.gymmanagement.core.models.Route
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

/**
 * Represents an item in the bottom navigation bar.
 *
 * This data class holds the necessary information to display and handle navigation
 * for a single item in a bottom navigation component.
 *
 * @property route The destination route associated with this item. This is typically a value
 *                 that identifies a specific screen or composable in the navigation graph.
 * @property label The text label to be displayed for this item.
 * @property icon The drawable resource representing the icon associated with this item.
 */
data class BottomNavigationItem<T>(
    val route: T,
    val label: StringResource,
    val icon: IconDrawable,
) {

    /**
     * Represents the drawable resources for an icon in its idle and active states.
     *
     * This is used to define different visual appearances for an icon based on whether
     * the associated navigation item is currently selected (active) or not (idle).
     *
     * @property stateIdle The drawable resource to use when the icon is in its idle (unselected) state.
     * @property stateActive The drawable resource to use when the icon is in its active (selected) state.
     */
    data class IconDrawable(
        val stateIdle: DrawableResource,
        val stateActive: DrawableResource
    )

}