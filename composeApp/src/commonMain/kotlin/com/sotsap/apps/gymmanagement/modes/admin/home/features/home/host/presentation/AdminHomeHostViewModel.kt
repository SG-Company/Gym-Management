package com.sotsap.apps.gymmanagement.modes.admin.home.features.home.host.presentation

import com.sotsap.apps.gymmanagement.core.lifecycle.BaseViewModel
import com.sotsap.apps.gymmanagement.core.network.supabase.extensions.sendEvent
import com.sotsap.apps.gymmanagement.modes.admin.home.navigation.AdminHomeNavigation
import com.sotsap.apps.gymmanagement.supabase_events.admin.SupabaseAdminHostEvents

/**
 * ViewModel for the Admin Home Host screen.
 *
 * This ViewModel manages the state and events for the main navigation container
 * within the admin section of the application. It handles user interactions
 * related to navigating between different admin features like Dashboard, Members, etc.
 *
 * @param S The state type for this ViewModel, aliased to [AdminHomeHostState].
 * @param E The event type for this ViewModel, aliased to [AdminHomeHostEvents].
 */
class AdminHomeHostViewModel: BaseViewModel<AdminHomeHostState, AdminHomeHostEvents>() {

    /**
     * Handles the selection of a navigation item.
     *
     * This function is called when a user selects an item from the navigation menu.
     * It updates the UI to reflect the selected item and performs any necessary actions
     * associated with that item.
     *
     * @param item The selected navigation item.
     */
    fun onNavigationItemSelected(item: AdminHomeNavigation) {
        val eventToSend = when (item) {
            is AdminHomeNavigation.Dashboard -> SupabaseAdminHostEvents.UserSelectDashboardOption
            is AdminHomeNavigation.Receipts -> SupabaseAdminHostEvents.UserSelectReceiptsOption
            is AdminHomeNavigation.Messages -> SupabaseAdminHostEvents.UserSelectMessagesOption
            is AdminHomeNavigation.Schedule -> SupabaseAdminHostEvents.UserSelectSchedulesOption
            is AdminHomeNavigation.Profile -> SupabaseAdminHostEvents.UserSelectProfileOption
        }
        update { currentState -> currentState.copy(selectedItem = item) }
        sendEvent(event = eventToSend)
    }

    override suspend fun initialState() = AdminHomeHostState()

}