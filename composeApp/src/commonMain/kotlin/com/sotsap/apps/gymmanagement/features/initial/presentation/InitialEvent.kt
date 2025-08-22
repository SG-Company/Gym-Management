package com.sotsap.apps.gymmanagement.features.initial.presentation

/**
 * Represents the events that can be triggered on the initial screen.
 * These events are used to communicate user interactions or other significant occurrences
 * from the UI to the ViewModel.
 */
sealed interface InitialEvent {

    /**
     * Event triggered when the login action is performed.
     */
    data object NavigateToLogin: InitialEvent

    /**
     * Event triggered when the home action is performed.
     */
    data object NavigateToHome: InitialEvent

}