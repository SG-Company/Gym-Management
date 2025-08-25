package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.profile.presentation

/**
 * Represents events that can occur in the admin profile screen.
 */
sealed interface ProfileAdminEvents {

    /**
     * Called when the logout button is clicked.
     */
    object OnLogoutClicked: ProfileAdminEvents

}