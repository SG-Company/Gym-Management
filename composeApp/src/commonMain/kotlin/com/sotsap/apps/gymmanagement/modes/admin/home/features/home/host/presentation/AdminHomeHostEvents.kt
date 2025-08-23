package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.host.presentation

/**
 * Represents the different events that can occur on the Admin Home Host screen.
 * These events are typically triggered by user interactions and are used to navigate
 * between different sections of the admin home.
 */
sealed interface AdminHomeHostEvents {
    data object OnDashboard: AdminHomeHostEvents
    data object OnReceipts: AdminHomeHostEvents
    data object OnMessages: AdminHomeHostEvents
    data object OnSchedule: AdminHomeHostEvents
    data object OnProfile: AdminHomeHostEvents
}