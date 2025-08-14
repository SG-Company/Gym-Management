package com.sotsap.apps.gymmanagement.features.login.presentation

/**
 * Represents the different events that can occur on the login screen.
 * These events are used to communicate user interactions from the UI to the ViewModel.
 */
sealed interface LoginEvents {

    /**
     * Event triggered when the user clicks the login button.
     */
    data object OnLogin: LoginEvents

    /**
     * Event triggered when the user clicks the register button.
     */
    data object OnRegister: LoginEvents

    /**
     * Event triggered when the user clicks the forgot password button.
     */
    data object OnForgotPassword: LoginEvents

    /**
     * Event triggered when the user clicks the back button.
     */
    data object OnBack: LoginEvents

}
