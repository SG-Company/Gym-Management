package com.sotsap.apps.gymmanagement.modes.admin.home.navigation

import com.sotsap.apps.gymmanagement.core.framework.components.navigation.BottomNavigationItem
import gymmanagement.composeapp.generated.resources.Res
import gymmanagement.composeapp.generated.resources.iconNavigationDashboardStateActive
import gymmanagement.composeapp.generated.resources.iconNavigationDashboardStateIdle
import gymmanagement.composeapp.generated.resources.iconNavigationMessageStateActive
import gymmanagement.composeapp.generated.resources.iconNavigationMessageStateIdle
import gymmanagement.composeapp.generated.resources.iconNavigationProfileStateActive
import gymmanagement.composeapp.generated.resources.iconNavigationProfileStateIdle
import gymmanagement.composeapp.generated.resources.iconNavigationReceiptStateActive
import gymmanagement.composeapp.generated.resources.iconNavigationReceiptStateIdle
import gymmanagement.composeapp.generated.resources.iconNavigationScheduleStateActive
import gymmanagement.composeapp.generated.resources.iconNavigationScheduleStateIdle
import gymmanagement.composeapp.generated.resources.navigationAdminDashboardLabel
import gymmanagement.composeapp.generated.resources.navigationAdminMessagesLabel
import gymmanagement.composeapp.generated.resources.navigationAdminProfileLabel
import gymmanagement.composeapp.generated.resources.navigationAdminReceiptsLabel
import gymmanagement.composeapp.generated.resources.navigationAdminScheduleLabel
import kotlinx.serialization.Serializable

/**
 * Represents the different navigation destinations within the admin home screen.
 * This sealed class is serializable, allowing it to be easily passed between different parts of the application,
 * for instance, when saving and restoring navigation state.
 *
 * Each object within this sealed class represents a distinct screen or section accessible from the admin home.
 */
@Serializable
sealed class AdminHomeNavigation(val route: String) {
    @Serializable object Dashboard: AdminHomeNavigation(route = "Dashboard")
    @Serializable object Receipts: AdminHomeNavigation(route = "Receipts")
    @Serializable object Messages: AdminHomeNavigation(route = "Messages")
    @Serializable object Schedule: AdminHomeNavigation(route = "Schedule")
    @Serializable object Profile: AdminHomeNavigation(route = "Profile")
}

/**
 * Defines the navigation item for the Dashboard screen in the admin home.
 * This item includes the route to the Dashboard, its label, and icons for both idle and active states.
 */
private val dashboardMenuItem = BottomNavigationItem<AdminHomeNavigation>(
    route = AdminHomeNavigation.Dashboard,
    label = Res.string.navigationAdminDashboardLabel,
    icon = BottomNavigationItem.IconDrawable(
        stateIdle = Res.drawable.iconNavigationDashboardStateIdle,
        stateActive = Res.drawable.iconNavigationDashboardStateActive
    )
)

/**
 * Defines the navigation item for the Receipts screen in the admin home.
 * This item includes the route to the Receipts screen, its label, and icons for both idle and active states.
 */
private val receiptsMenuItem = BottomNavigationItem<AdminHomeNavigation>(
    route = AdminHomeNavigation.Receipts,
    label = Res.string.navigationAdminReceiptsLabel,
    icon = BottomNavigationItem.IconDrawable(
        stateIdle = Res.drawable.iconNavigationReceiptStateIdle,
        stateActive = Res.drawable.iconNavigationReceiptStateActive
    )
)

/**
 * Defines the navigation item for the Messages screen in the admin home.
 * This item includes the route to the Messages screen, its label, and icons for both idle and active states.
 */
private val messagesMenuItem = BottomNavigationItem<AdminHomeNavigation>(
    route = AdminHomeNavigation.Messages,
    label = Res.string.navigationAdminMessagesLabel,
    icon = BottomNavigationItem.IconDrawable(
        stateIdle = Res.drawable.iconNavigationMessageStateIdle,
        stateActive = Res.drawable.iconNavigationMessageStateActive
    )
)

/**
 * Defines the navigation item for the Schedule screen in the admin home.
 * This item includes the route to the Schedule screen, its label, and icons for both idle and active states.
 */
private val scheduleMenuItem = BottomNavigationItem<AdminHomeNavigation>(
    route = AdminHomeNavigation.Schedule,
    label = Res.string.navigationAdminScheduleLabel,
    icon = BottomNavigationItem.IconDrawable(
        stateIdle = Res.drawable.iconNavigationScheduleStateIdle,
        stateActive = Res.drawable.iconNavigationScheduleStateActive
    )
)

/**
 * Defines the navigation item for the Profile screen in the admin home.
 * This item includes the route to the Profile screen, its label, and icons for both idle and active states.
 */
private val profileMenuItem = BottomNavigationItem<AdminHomeNavigation>(
    route = AdminHomeNavigation.Profile,
    label = Res.string.navigationAdminProfileLabel,
    icon = BottomNavigationItem.IconDrawable(
        stateIdle = Res.drawable.iconNavigationProfileStateIdle,
        stateActive = Res.drawable.iconNavigationProfileStateActive
    )
)

/**
 * A list of [BottomNavigationItem] objects that define the primary navigation structure for the admin home screen.
 * This list is used to populate the bottom navigation bar, providing users with quick access to the main sections
 * of the admin interface, such as Dashboard and Receipts.
 *
 * Each item in the list is configured with its route, label, and icons, ensuring a consistent and intuitive
 * navigation experience.
 */
val adminHomeNavigationItems = listOf(
    dashboardMenuItem,
    receiptsMenuItem,
    messagesMenuItem,
    scheduleMenuItem,
    profileMenuItem
)