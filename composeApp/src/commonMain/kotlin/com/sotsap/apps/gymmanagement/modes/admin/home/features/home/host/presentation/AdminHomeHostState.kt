package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.host.presentation

import com.sotsap.apps.gymmanagement.modes.admin.home.navigation.AdminHomeNavigation


/**
 * Represents the state of the admin home host screen.
 *
 * @property selectedItem The currently selected navigation item in the admin home screen. Defaults to [AdminHomeNavigation.Dashboard].
 */
data class AdminHomeHostState(
    val selectedItem: AdminHomeNavigation = AdminHomeNavigation.Dashboard,
)
