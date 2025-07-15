package com.sotsap.apps.gymmanagement.features.login.presentation

import com.sotsap.apps.gymmanagement.core.presentation.BaseIntent

/**
 * Represents the different intents that can be triggered on the Login screen.
 * These intents are used to communicate user actions from the UI to the ViewModel.
 */
sealed interface LoginIntents {

    /**
     * Represents the intents that can be triggered by the user in the login screen.
     * These intents are used to communicate user actions to the ViewModel.
     */
    object OnLogin: LoginIntents

    /**
     * Represents the intent to navigate to the registration screen.
     * This intent is triggered when the user indicates a desire to create a new account.
     */
    object OnRegister: LoginIntents

    /**
     * Represents an intent to close the current screen or dialog.
     * This is a common intent used across various parts of the application
     * to signal a desire to dismiss the current view.
     */
    object Close: LoginIntents

}